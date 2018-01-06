package com.cs.data.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.cs.common.entityenum.BookState;
import com.cs.common.utils.DateUtils;

public class JDBCExcel {

	private static String user = null;
	private static String pass = null;
	private static String url = null;

	/**
	 * 获取数据库连接
	 */
	public static void getParam() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(Thread.currentThread().getContextClassLoader().getResource("jdbc.properties").getPath())));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//返回获取的值
		url = prop.getProperty("jdbc.url");
		user = prop.getProperty("jdbc.username");
		pass = prop.getProperty("jdbc.password");
	}
	
	/**
	 * 连接数据库
	 * 
	 * @return
	 */
	public static Connection createConnection() {
		Connection conn = null;// 连接对象
		if(url==null||user==null||pass==null){
			getParam();
		}
		String sDBDriver = "oracle.jdbc.driver.OracleDriver";

		try {
			Class.forName(sDBDriver).newInstance();
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection con,PreparedStatement ps,ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (SQLException e) {
					System.out.println("数据库关闭失败");
					e.printStackTrace();
				} 
			}
		}
	}

	/**
	 * 查询语句 返回结果集
	 * 
	 * @param select
	 * @return
	 */
	public static ResultSet selectSql(Connection conn,String select) {
		ResultSet rs = null;// 结果集对象
		Statement sm = null;
		conn = createConnection();
		try {
			sm = conn.createStatement();
			rs = sm.executeQuery(select);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导数据到内网  ，数据导入，id冲突的失败不计入失败，如果是取消或者完成表，导入数据后，更新预约状态
	 * @param excelPath
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> batchInsert(String excelPath, String tableName)throws IOException{
		
		long start = System.currentTimeMillis();
		
		Connection conn = null;// 连接对象
		PreparedStatement insPs = null;
		PreparedStatement updPs = null;
		PreparedStatement _ps = null;
		
		conn = createConnection();//获取数据连接

		//errorMap插入异常集合，keyIndex记录导入数据的excel中id字段的下标
		Map<String, String> errorMap = new HashMap<String, String>();
		
		//导入excel
		FileInputStream fis = new FileInputStream(excelPath);
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		
		//获取excel中字段列表，并确定ID字段下标
		int excelColCount = row.getPhysicalNumberOfCells();//excel列数
		List<String> excelColList = new ArrayList<String>();//excel字段列表
		for (int i = 0; i < excelColCount ; i++) {
			excelColList.add(row.getCell(i).getStringCellValue());
		}
		
		try{
			//获取数据库字段列表
			ResultSet rsCol = selectSql(conn,"SELECT * FROM " + tableName + " WHERE ROWNUM  <= 0");//获取列
			if(rsCol == null){
				errorMap.put(tableName, tableName+"表不存在或者没有数据");
				return errorMap;
			}
			ResultSetMetaData rsmd = rsCol.getMetaData();
			int dbColCount = rsmd.getColumnCount(); //数据库列数
			List<String> dbColList = new ArrayList<String>();//数据库字段列表
			for (int i = 0; i < dbColCount; i++) {
				dbColList.add(rsmd.getColumnName(i+1));
			}
			
			//取字段交集，解决字段增减的问题
			List<String> newColList = new ArrayList<String>();
			for (int i = 0; i < dbColList.size(); i++) {
				newColList.add(dbColList.get(i));
			}
			newColList.retainAll(excelColList);
			//导入字段到VO
			Map<String ,Object> VO  =  new HashMap<String, Object>(); 
			for (int i = 0; i < newColList.size(); i++) {
				VO.put(newColList.get(i), null);
			}
			
			//获取插入SQL字符串
			StringBuffer columnBf = new StringBuffer();
			StringBuffer valueBf = new StringBuffer();
			for (int i = 0; i < newColList.size(); i++) {
				columnBf.append(newColList.get(i));
				valueBf.append("?");
				if (i < newColList.size()  - 1) {
					columnBf.append(", ");
					valueBf.append(", ");
				}
			}
			
			insPs = conn.prepareStatement("INSERT INTO "+ tableName + "(" + columnBf.toString() + ") VALUES(" + valueBf.toString() + ")");
			
			
			List<String> updColist = new ArrayList<String>();
			for (int i = 0; i < newColList.size(); i++) {
				updColist.add(newColList.get(i));
			}
			
			if(tableName.equals("BOOK_INFO")){
				updColist.remove("ID");
				//获取更新SQL字符串
				StringBuffer sqlBf = new StringBuffer();
				for (int i = 0; i < updColist.size(); i++) {
					String key = updColist.get(i);
					if(!"ID".equals(key)){
						sqlBf.append(updColist.get(i) + " = ? ");
						if (i < updColist.size()  - 1) {
							sqlBf.append(" , ");
						}
					}
				}
				updColist.add("ID");
				updPs = conn.prepareStatement("UPDATE "+ tableName + " SET "+sqlBf+" WHERE ID = ?");
				//System.out.println("UPDATE "+ tableName + " SET "+sqlBf+" WHERE ID = ?");
			}
			
			//读取行。第一行为字段名忽略
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				sheet = wb.getSheetAt(k);
				if(sheet==null){
					continue;
				}
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					//读取行中列将值传入VO
					for (int i = 0; i < excelColCount; i++) {
						String field = excelColList.get(i);
						//判断当前循环的excel列 是否存在交集中
						if(!VO.containsKey(field)){
							continue;
						}
						Cell cell = row.getCell(i);
						if(cell == null){
							VO.put(field,null);
							continue;
						}
						//System.out.println(field +":" +cell.getStringCellValue());
						String value = null;
						try{
							value = cell.getStringCellValue();
						}catch (java.lang.IllegalStateException e) {
							value = String.valueOf(cell.getNumericCellValue());
						}
						
						if(value != null&&!"".equals(value)){
							VO.put(field,value);
						}else{
							VO.put(field,null);
						}
					}

					boolean fail = false;
					
					//数据类型转化
					for (Map.Entry<String, Object> entry : VO.entrySet()) {
						if(entry.getValue() == null){
							continue;
						}
						String key = entry.getKey();
						String value = String.valueOf(entry.getValue());
						
						int dbIndex = dbColList.indexOf(key)+1;
						int typeCode = rsmd.getColumnType(dbIndex);
						try {
							if(typeCode == 91||typeCode==92||typeCode==93){
									Date date = DateUtils.parse(value,"yyyyMMddHHmmss");
									if(date == null){
										errorMap.put(String.valueOf(VO.get("ID")),key+ "--时间类型格式化失败");
										fail = true;
										break;
									}
									Long data = date.getTime();
									VO.put(key, new java.sql.Timestamp(data));
							}else if(typeCode == 2||typeCode==3||typeCode==4){
								int data = Integer.valueOf(value.split("\\.")[0]);
								VO.put(key, data);
							}else{
								VO.put(key, value);
							}
						} catch (Exception e) {
							e.printStackTrace();
							errorMap.put(String.valueOf(VO.get("ID")), key+"--"+ e.getMessage());
							fail = true;
							break;
						}
					}
					
					if(fail){
						continue;
					}
					insPs.clearParameters();
					for (int i = 0; i < newColList.size(); i++) {
						insPs.setObject(i+1, VO.get(newColList.get(i)));
					}
					
					try{
						insPs.executeUpdate();
						if(tableName.equals("COMPLETE")||tableName.equals("CANCEL")){//导入的是状态表,更新状态
							_ps =	conn.prepareStatement("UPDATE BOOK_INFO SET BOOK_STATE = ? WHERE BOOK_NUMBER = ?");
							if(tableName.equals("COMPLETE")){
								_ps.setInt(1, BookState.YYWC.getIndex());
							}else{
								_ps.setInt(1, BookState.YYQX.getIndex());
							}
							_ps.setString(2, String.valueOf(VO.get("BOOK_NUMBER")));
							_ps.executeUpdate();
							_ps.close();
						}
					}catch (SQLException e) {
						if( 1 != e.getErrorCode()){//排除重复id的
							errorMap.put(String.valueOf(VO.get("ID")), e.getMessage());
						}/*if(tableName.equals("BOOK_INFO")){ //外网导入专网只插入，不做更新
							//System.out.println("更新BOOK_INFO："+String.valueOf(VO.get("ID")));
							try {
								for (int i = 0; i < updColist.size(); i++) {
										updPs.setObject(i+1, VO.get(updColist.get(i)));
								}
								updPs.executeUpdate();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}*/
					}
				}
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConnection(conn,insPs,null);
			closeConnection(conn,updPs,null);
			closeConnection(conn,_ps,null);
		}
		long end = System.currentTimeMillis();
		System.out.println(tableName+"批量插入需要时间:" + (end - start)); // 批量插入需要时间
		return errorMap;
	}

	
	/**
	 * 导出数据
	 * @param excelPath excel存放路径
	 * @param tableName 表名
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param param 导出字段的字符串  例 ： "ID,TYPE,NUMBER"
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void exportBigDataExcel(String excelPath, String tableName,String beginDate, String endDate,String param) throws IOException, SQLException{

		// 获取连接
		Connection conn = null;// 连接对象
		PreparedStatement ps = null;
		ResultSet rs = null;// 结果集对象
		
		conn = createConnection();

		if(param == null||param.equals("")){
			param="*";
		}
		
		//处理 不到出预约状态<预约中 2><取消 4>
		if(tableName.equals("BOOK_INFO")){
			ps = conn.prepareStatement("SELECT "+param+" FROM BOOK_INFO b RIGHT JOIN (SELECT c.BOOK_NUMBER FROM BOOK_INFO_CHANGE c where c.CREATE_DATE > TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') AND c.CREATE_DATE < TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') group by c.BOOK_NUMBER)f  ON b.BOOK_NUMBER = f.BOOK_NUMBER");
			//ps = conn.prepareStatement("SELECT "+param+" FROM "+tableName+" WHERE CREATE_DATE >= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND CREATE_DATE <= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND BOOK_NUMBER NOT LIKE 'G%' AND BOOK_NUMBER NOT LIKE 'D%'");
		}else{
			ps = conn.prepareStatement("SELECT "+param+" FROM "+tableName+" WHERE CREATE_DATE >= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') AND CREATE_DATE <= TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')");
		}
		ps.setString(1,beginDate);
		ps.setString(2,endDate);
		rs = ps.executeQuery();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsCount = rsmd.getColumnCount(); // 列数

		Workbook wb = null;
		wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();// 新建工作簿
		
		Row row = sheet.createRow(0);
		// 写标题
		for (int i = 0; i < columnsCount; i++) {
			row.createCell(i).setCellValue(rsmd.getColumnName(i + 1));
		}

		boolean f = false;
		try {
			if (!rs.next()) {
				return;
			}
			
			int sheetNum = 0;
			while (!f) {
				if(rs.getRow()%50000 == 0){
					sheet = wb.createSheet();
					sheetNum++;
				}
				if(sheetNum > 0){
					row = sheet.createRow(rs.getRow() - sheetNum*50000+1);
				}else{
					row = sheet.createRow(rs.getRow());
				}
				for (int i = 0; i < columnsCount; i++) {
					Cell cell = row.createCell(i);
					//时间类型处理
					if (rsmd.getColumnType(i+1) == 91|| rsmd.getColumnType(i+1) == 92|| rsmd.getColumnType(i+1) == 93) {
						Date date = rs.getTimestamp(i+1);
						if (date != null) {
							DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
							cell.setCellValue(df.format(date));
						}else{
							cell.setCellValue("");
						}
					} else {
						cell.setCellValue(rs.getString(i+1));
					}
				}
				if (!rs.next()) {
					f = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}finally{
			closeConnection(conn,ps,rs);
		}

		File tmpFile = new File(excelPath);
		if (!tmpFile.exists()) {
			tmpFile.getParentFile().mkdirs();
			tmpFile.createNewFile();
		}
		FileOutputStream fileOut = new FileOutputStream(tmpFile);
		wb.write(fileOut);
		fileOut.close();
	}

	public static void main(String[] args){
		
		List<String> list = new ArrayList<String>();
		list.add("AAAA");
		list.add("Bdasdsa");
		list.add("C");
		Object[] s = list.toArray();
		
		System.out.println(s[1]);
		
		
	}
}


