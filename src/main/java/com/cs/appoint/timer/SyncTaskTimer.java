package com.cs.appoint.timer;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.appoint.entity.SyncTask;
import com.cs.appoint.service.SyncTaskService;
import com.cs.common.constant.Constants;
import com.cs.common.utils.RequestUtils;
import com.cs.common.utils.database.Transformer;
import com.cs.common.utils.database.impl.DefaultTransformer;
import com.cs.webservice.dto.SynchronizeEntity;
//@Component(value = "SyncTaskTimer") 
public class SyncTaskTimer {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SyncTaskService syncTaskService;
	
	/** 调用URL */
	public static String url = Constants.SYN_BOOK_INFO_URL;

	/** 命名空间 */
	private static String QUERY = Constants.SYN_BOOK_INFO_QUERY;
	private static String WRITE = Constants.SYN_BOOK_INFO_WRITE;
	
	public SyncTaskTimer(){
		System.out.println("--------------------------------");
		System.out.println("实例化");
		System.out.println("--------------------------------");
	}
	
	private static Map<String, Object> getResultMap(ResultSet rs)
			throws SQLException {
		Map<String, Object> hm = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String key = rsmd.getColumnLabel(i);
			Object value = rs.getObject(i);
			hm.put(key, value);
		}
		return hm;
	}  
	/**
	 * 定时生成同步任务
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-8 下午5:37:45
	 */
	public void createSyncTask() throws Exception{
		System.out.println("线程createSyncTask："+Thread.currentThread().getName());
		List<String> tableNames = new ArrayList<String>();
		tableNames.add("COMPLETE");
		Thread.sleep(30000);
		for(String tableName : tableNames){
//			ResultSet rs = dataSource.getConnection().prepareStatement("select * from "+tableName).executeQuery();
//			while(rs.next()){
//				Map<String,Object> test = getResultMap(rs);
//				System.out.println(test);
//			}
			List<Map<String,Object>> list = syncTaskService.findNotSyncData(tableName);
			if(CollectionUtils.isEmpty(list)){
				continue;
			}
			for (int j = 0; j < list.size(); j++) {
				//根据生成单条同步任务
				Map<String, Object> map = list.get(j);
				StringBuffer soapResultData = new StringBuffer();
				// 生成同步数据XML信息
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String colName = entry.getKey();
					Object data = entry.getValue();
					String clazz = entry.getValue().getClass().getSimpleName();
					if (clazz.equals("TIMESTAMP")) {
						data = getDate(data);
					}
					soapResultData.append("<" + colName + " class=\"" + clazz + "\">" + data + "</" + colName + ">");
				}
				System.out.println(soapResultData.toString());
				SyncTask syncTask = new SyncTask((String) map.get("ID"), tableName, soapResultData.toString());
				syncTaskService.insert(syncTask);
			}
		}
		System.out.println("线程createSyncTask："+Thread.currentThread().getName()+"结束………………");
	}
	
	public void syncToService(){
		try {
			String ResponseXml = RequestUtils.queryObject(url,QUERY,getReqXml());
			write(ResponseXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String colName, String xmlDoc) {
		int startIndex = -1;
		int endIndex = -1;
		startIndex = xmlDoc.indexOf("<" + colName + ">");
		endIndex = xmlDoc.indexOf("</" + colName + ">");
		if (startIndex >= 0 && endIndex >= 0) {
			return xmlDoc.substring(startIndex + colName.length() + 2, endIndex);
		} else {
			return null;
		}
	}
	
	/**
	 * 同步完成，发送至服务端，服务端更新任务状态
	 * @param id
	 * @Author        succ
	 * @date 2017-11-2 下午2:18:25
	 */
	public void finishSync(String id){
		String idXml =  getReqXml("<id>"+id+"</id>");
		try {
			String ResponseXml = RequestUtils.queryObject(url,WRITE,idXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(String WriteXmlDoc) throws Exception{
		// 对请求报文做处理
				List<SynchronizeEntity> list = null;
				String id = getValue("id",WriteXmlDoc);
				WriteXmlDoc = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
						"<root><tables>"+ getValue("tables",WriteXmlDoc)+"</tables></root>";
				try {
					System.out.println(WriteXmlDoc);
					list = getSynchronizeEntityFromXml(WriteXmlDoc);
				} catch (Exception e) {
					System.out.println(e);
					return;
				}
				if (CollectionUtils.isNotEmpty(list)) {
					Connection connection = null;
					try {
						connection = dataSource.getConnection();
						for (SynchronizeEntity synchronizeEntity : list) {
							try {
								//获取插入 SQL 语句
								String insertSQL = getInsertSQL(synchronizeEntity.getTableName(),synchronizeEntity.getColumns());
								PreparedStatement pstmt = connection.prepareStatement(insertSQL);
								Map<String,String> types = getColumnsType(connection, synchronizeEntity.getTableName());
								Transformer trans = new DefaultTransformer();
								for(int i=0; i<synchronizeEntity.getValues().size(); i++){
									int index = i+1;
									String columnName = synchronizeEntity.getColumns().get(i);
									String columnValue = synchronizeEntity.getValues().get(i);
									String columnType = types.get(columnName);
									
									if (columnType.toUpperCase().indexOf("BLOB") != -1) {
										pstmt.setBinaryStream(index, (InputStream) trans
												.transform(columnValue, columnType));
									} else if (columnType.toUpperCase().indexOf("CLOB") != -1) {
										pstmt.setCharacterStream(index,
												(Reader) trans.transform(columnValue, columnType));
									} else if (columnType.toUpperCase().indexOf("DATE") != -1) {
										Date date = (Date) trans
												.transform(columnValue, columnType);
										java.sql.Date dt = new java.sql.Date(date.getTime());
										pstmt.setDate(index, dt);
									} else {
										pstmt.setObject(index,
												trans.transform(columnValue, columnType));
									}
								}
								pstmt.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						
						//插入完成，发送成功状态
						finishSync(id);
					} catch (Exception e) {
						e.printStackTrace();
					} finally{
						connection.close();
					}
				}
	}

	/**
	 * 从写入XML中获取同步数据实体
	 * @param synXml
	 * @return
	 * @throws DocumentException
	 * @Author        succ
	 * @date 2017-10-28 下午4:26:24
	 */
	@SuppressWarnings("unchecked")
	public List<SynchronizeEntity> getSynchronizeEntityFromXml(String WriteXmlDoc) throws DocumentException{
		List<SynchronizeEntity> result = new ArrayList<SynchronizeEntity>();
		
		Document doc = DocumentHelper.parseText(WriteXmlDoc);
		Element root = doc.getRootElement();
		List<Element> tables = root.element("tables").elements("table");
		//obj下元素的顺序可能不是固定的 [{name:"scc",age:"22"},{age:"22",name:"scc"}]
		for(Element table : tables){
			Element objsContainer = table.element("objs");
			List<Element> objs = objsContainer.elements("obj");
			List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>(); 
			for(Element obj : objs){
				SynchronizeEntity synchronizeEntity = new SynchronizeEntity();
				synchronizeEntity.setTableName(table.elementText("name"));//表名
				
				List<Element> attrs = obj.elements();
				List<String> columns = new ArrayList<String>();//列名
				List<String> values = new ArrayList<String>();//列值
				
				for(Element attr : attrs){
					columns.add(attr.getName());
					values.add(attr.getTextTrim());
				}
				synchronizeEntity.setColumns(columns);
				synchronizeEntity.setValues(values);
				result.add(synchronizeEntity);
			}
		}
		return result;
	}

	/**
	 * 获取插入SQL语句
	 * @param tableName
	 * @param columnsName
	 * @return
	 * @Author        succ
	 * @date 2017-10-30 下午4:51:13
	 */
	private String getInsertSQL(String tableName,List<String> columnsName){
		StringBuilder sb = new StringBuilder("INSERT INTO "+tableName+"(");
		StringBuilder columnsValue = new StringBuilder();
		for(int i=0; i<columnsName.size(); i++){
			columnsValue.append("?");
			sb.append(columnsName.get(i));
			if(i != columnsName.size()-1){
				sb.append(",");
				columnsValue.append(",");
			}
		}
		sb.append(") values (");
		sb.append(columnsValue.toString() + ")");
		return sb.toString();
	}

	/**
	 * 获取数据表的列类型集合
	 * @param connection
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @Author        succ
	 * @date 2017-10-30 下午4:51:32
	 */
	private Map<String,String> getColumnsType(Connection connection,String tableName) throws SQLException{
		Map<String,String> types = new HashMap<String,String>();
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet rs = dbmd.getColumns(connection.getCatalog(),dbmd.getUserName(), tableName.toUpperCase(), "%");
		while (rs.next()) {
			String columnName = rs.getString("COLUMN_NAME");
			String columnType = rs.getString("TYPE_NAME");
			types.put(columnName, columnType);
		}
		return types;
	}
	
	
	public static String getReqXml() {
		//获取同步的请求参数
		String syncParam = "<xtlb>18</xtlb><jkid>1</jkid><jkxlh>12121</jkxlh>";
		StringBuffer soapResultData = new StringBuffer();
		soapResultData.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><root><head>")
		.append(syncParam).append("</head></root>");
		return soapResultData.toString();
	}

	public static String getReqXml(String idXml) {
		//获取同步的请求参数
		String syncParam = "<xtlb>18</xtlb><jkid>2</jkid><jkxlh>12121</jkxlh>";
		
		StringBuffer soapResultData = new StringBuffer();
		soapResultData.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><root><head>")
		.append(syncParam+idXml).append("</head></root>");
		return soapResultData.toString();
	}
	
	private String getDate(Object value) {
		Timestamp timestamp = null;
		try {
			timestamp = (Timestamp) value;
		} catch (Exception e) {
			timestamp = getOracleTimestamp(value);
		}
		if (timestamp != null)
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"))
					.format(timestamp);
		else
			return null;
	}

	/**
	 * @reference oracle.sql.Datum.timestampValue();
	 * @return
	 */
	private Timestamp getOracleTimestamp(Object value) {
		try {
			Class clz = value.getClass();
			Method m = clz.getMethod("timestampValue", null);
			// m = clz.getMethod("timeValue", null); 时间类型
			// m = clz.getMethod("dateValue", null); 日期类型
			return (Timestamp) m.invoke(value, null);

		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><tables><table><name>BOOK_INFO</name><objs><obj><BOOKER_NAME>家居服</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>居民身份证</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031225795</BOOK_NUMBER><ID_NUMBER>452230199302021022</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>GHJHJHJH</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD99D014B2D4F6BE0536B01A8C03D0F</ID><PLAT_NUMBER>粤BVDVVVF</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>e4e48584399473d20139947f125e2b2c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031202368</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD80654A7384B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>dsssdsd</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031006871</BOOK_NUMBER><ID_NUMBER>DSDSD</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>DSDSD</FRAME_NUMBER><MOBILE>13677194063</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD87CC2D3974CD6E0536B01A8C0CA92</ID><PLAT_NUMBER>粤BDSDSD</PLAT_NUMBER><VEHICLE_TYPE>3</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-10-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031203913</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD80654A7334B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>4</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>dsssdsd</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>17103116049A</BOOK_NUMBER><ID_NUMBER>DSDSD</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>DSDSD</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD4B4F9D4774654E0536B01A8C08AAE</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>3</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-10-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031207548</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD80654A7354B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>4</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>dsssdsd</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>17103100675F</BOOK_NUMBER><ID_NUMBER>DSDSD</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>DSDSD</FRAME_NUMBER><MOBILE>13677194063</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD8F86692AD4E02E0536B01A8C0C5BE</ID><PLAT_NUMBER>粤BDSDSD</PLAT_NUMBER><VEHICLE_TYPE>3</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-10-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031200010</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD80654A7304B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤JHJHJHJ</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>4</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>日返回给</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><DRIVER_TYPE>0</DRIVER_TYPE><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>17110112862F</BOOK_NUMBER><ID_NUMBER>GFGHGBVBRG</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>HGLFGH254322654</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CE5371F46F25C41E0536B01A8C065A2</ID><PLAT_NUMBER>粤BBFFHGH</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-11-01 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-04</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>家居服</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>居民身份证</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031225795</BOOK_NUMBER><ID_NUMBER>452230199302021022</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>GHJHJHJH</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD99D014B2D4F6BE0536B01A8C03D0F</ID><PLAT_NUMBER>粤BVDVVVF</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>e4e48584399473d20139947f125e2b2c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031202368</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD80654A7384B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>dsssdsd</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031006871</BOOK_NUMBER><ID_NUMBER>DSDSD</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>DSDSD</FRAME_NUMBER><MOBILE>13677194063</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD87CC2D3974CD6E0536B01A8C0CA92</ID><PLAT_NUMBER>粤BDSDSD</PLAT_NUMBER><VEHICLE_TYPE>3</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-10-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031203913</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD80654A7334B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>4</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>dsssdsd</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>17103116049A</BOOK_NUMBER><ID_NUMBER>DSDSD</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>DSDSD</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD4B4F9D4774654E0536B01A8C08AAE</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>3</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-10-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031207548</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CD80654A7354B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤DSDSD</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>4</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>dsssdsd</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>17103100675F</BOOK_NUMBER><ID_NUMBER>DSDSD</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>DSDSD</FRAME_NUMBER><MOBILE>13677194063</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD8F86692AD4E02E0536B01A8C0C5BE</ID><PLAT_NUMBER>粤BDSDSD</PLAT_NUMBER><VEHICLE_TYPE>3</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-10-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>gfhgh</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CC1465686DA2E69E0536B01A8C0DF3B</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>171031200010</BOOK_NUMBER><ID_NUMBER>HJHJHJHJH</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>14:00-18:00</BOOK_TIME><FRAME_NUMBER>JHJHFCXCDSF</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>南山交警大队</STATION_NAME><ID>5CD80654A7304B91E0536B01A8C0CBED</ID><PLAT_NUMBER>粤JHJHJHJ</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>4</BOOK_STATE><CREATE_DATE>2017-10-31 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-02</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj><obj><BOOKER_NAME>日返回给</BOOKER_NAME><FUEL_TYPE>0</FUEL_TYPE><STATION_ID>5CBC227DEDE525ECE0536B01A8C0690F</STATION_ID><ID_TYPE_NAME>组织机构代码证书</ID_TYPE_NAME><DRIVER_TYPE>0</DRIVER_TYPE><CAR_TYPE_NAME>小型汽车</CAR_TYPE_NAME><BOOK_CHANNEL>1</BOOK_CHANNEL><BOOK_NUMBER>17110112862F</BOOK_NUMBER><ID_NUMBER>GFGHGBVBRG</ID_NUMBER><REQUEST_IP>127.0.0.1</REQUEST_IP><USE_CHARATER>A</USE_CHARATER><BOOK_TIME>09:00-12:00</BOOK_TIME><FRAME_NUMBER>HGLFGH254322654</FRAME_NUMBER><MOBILE>18654223531</MOBILE><STATION_NAME>深圳市通盛汽车检测站</STATION_NAME><ID>5CE5371F46F25C41E0536B01A8C065A2</ID><PLAT_NUMBER>粤BBFFHGH</PLAT_NUMBER><VEHICLE_TYPE>1</VEHICLE_TYPE><ID_TYPE_ID>40288282463ceca50146462942d3055c</ID_TYPE_ID><BOOK_STATE>1</BOOK_STATE><CREATE_DATE>2017-11-01 00:00:00.0</CREATE_DATE><BOOK_DATE>2017-11-04</BOOK_DATE><VEHICLE_CHARACTER>1</VEHICLE_CHARACTER><CAR_TYPE_ID>402881e44e345afa014e345c4a700001</CAR_TYPE_ID></obj></objs></table></tables></root>";
		try {
			new SyncTaskTimer().write(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
