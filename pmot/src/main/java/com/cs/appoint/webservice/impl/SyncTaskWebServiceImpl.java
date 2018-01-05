package com.cs.appoint.webservice.impl;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.entity.SyncTask;
import com.cs.appoint.service.BookInfoService;
import com.cs.appoint.service.SyncTaskService;
import com.cs.appoint.webservice.SyncTaskWebService;
import com.cs.common.entityenum.BookState;
import com.cs.common.entityenum.SyncState;
import com.cs.common.exception.InvalidXMLArgumentException;
import com.cs.common.utils.database.Transformer;
import com.cs.common.utils.database.impl.DefaultTransformer;
import com.cs.common.utils.webservice.ResultCode;
import com.cs.common.utils.webservice.WebServiceXmlUtil;
import com.cs.mvc.dao.SqlCondition;
import com.cs.webservice.dto.SynchronizeEntity;

@Service
@Transactional
public class SyncTaskWebServiceImpl implements SyncTaskWebService {

	@Autowired
	private SyncTaskService syncTaskService;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private BookInfoService bookInfoService;
	
	@Override
	public String findNotSyncData() throws Exception {
		//查询所有未同步的、在配置
		List<String> tableNames = new ArrayList<String>();
		tableNames.add("COMPLETE");
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("STATE = ", SyncState.WAIT_SYNC);
		sqlCondition.addSingleNotNullCriterion("TABLE_NAME IN ", tableNames);
		List<SyncTask> syncTaskList = syncTaskService.findByCondition(sqlCondition);
		if (syncTaskList.size() == 0) {
			return buildXmlFromSyncTask(null);
		}
		// 更新开始同步时间
		syncTaskService.startSyncTask(syncTaskList);
		// 获取请求XML
		String strXml = this.buildXmlFromSyncTask(syncTaskList);
		return strXml;
	}

	@Override
	public String finishSyncTask(String ids) throws Exception {
		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			List<String> idList = new ArrayList<String>();
			for(String id : idArr){
				idList.add(id);
			}
			syncTaskService.finishSyncTask(idList);
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS,"服务端信息提示：更新完成");
		}else{
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL,"服务端信息提示：参数【ids】为空");
		}
	}


	@Override
	public String importData(String xmlData) throws Exception {
		List<SynchronizeEntity> list = getSynchronizeEntityFromXml(xmlData);
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
						if("CANCEL".equals(synchronizeEntity.getTableName())){
							int bookNumberIndex = synchronizeEntity.getColumns().indexOf("BOOK_NUMBER");
							String bookNumber = synchronizeEntity.getValues().get(bookNumberIndex);
							bookInfoService.updateByBookNumber(BookState.YYQX.getIndex(), bookNumber);
						}
					} catch (java.sql.SQLException e1) {//如果同一条记录重复插入，会违反唯一约束条件，此时继续处理下一条记录
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR);
			} finally{
				connection.close();
			}
		}
		return WebServiceXmlUtil.buildResponseXml(ResultCode.SUCCESS);
	}
	
	
	@SuppressWarnings("unchecked")
	private List<SynchronizeEntity> getSynchronizeEntityFromXml(String xmlData) throws DocumentException, InvalidXMLArgumentException{
		List<SynchronizeEntity> result = new ArrayList<SynchronizeEntity>();
		
		Document doc = DocumentHelper.parseText(xmlData);
		Element tablesEle = doc.getRootElement();
		if(tablesEle == null || !"tables".equals(tablesEle.getName())){
			throw new InvalidXMLArgumentException("服务器端提示信息：XML格式错误，【tables】元素不能为空");
		}
		List<Element> tables = tablesEle.elements("table");
		for(Element table : tables){
			Element objsContainer = table.element("objs");
			if(objsContainer == null){
				throw new InvalidXMLArgumentException("服务器端提示信息：XML格式错误，【objs】元素不能为空");
			}
			String tableName = table.elementText("name");
			if(StringUtils.isBlank(tableName)){
				throw new InvalidXMLArgumentException("服务器端提示信息：XML格式错误，表名不能为空");
			}
			List<Element> objs = objsContainer.elements("obj");
			for(Element obj : objs){
				SynchronizeEntity synchronizeEntity = new SynchronizeEntity();
				synchronizeEntity.setTableName(tableName);//表名
				
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
	 * 根据同步任务列表构建XML报文
	 * @param syncTaskList
	 * @return
	 * @throws UnsupportedEncodingException
	 * @Author        succ
	 * @date 2017-11-9 下午3:37:33
	 */
	private String buildXmlFromSyncTask(List<SyncTask> syncTaskList) throws UnsupportedEncodingException {
		StringBuffer soapResultData = new StringBuffer();
		if(CollectionUtils.isEmpty(syncTaskList)){
			soapResultData.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><root><head>")
			.append("<code>-1</code><message>没有同步任务</message>").append("</head><body><tables>");
//			return URLEncoder.encode(soapResultData.toString(), "utf-8");
			return soapResultData.toString();
		}
		// 利用map 将 tableName相同的dateXml组合到同一个key下面
		Map<String, String> syncTaskMap = new HashMap<String, String>();
		
		StringBuilder syncTaskIds = new StringBuilder();
		for (int i = 0; i < syncTaskList.size(); i++) {
			syncTaskIds.append(syncTaskList.get(i).getId()+",");
			String tableName = syncTaskList.get(i).getTableName();
			String dataXmls = syncTaskMap.get(tableName);
			if (dataXmls == null) {
				dataXmls = "";
			}
			dataXmls += "<obj>" + syncTaskList.get(i).getDataXml() + "</obj>";
			syncTaskMap.put(tableName, dataXmls);
		}
		// 获取同步的请求参数
		
		soapResultData.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><root><head>")
				.append("<code>1</code><message>同步成功</message><ids>"+syncTaskIds.toString()+"</ids>").append("</head><body><tables>");
		for (Map.Entry<String, String> entry : syncTaskMap.entrySet()) {
			soapResultData.append("<table><name>").append(entry.getKey())
					.append("</name>").append("<objs>")
					.append(entry.getValue()).append("</objs></table>");
		}
		soapResultData.append("</tables></body></root>");
//		return URLEncoder.encode(soapResultData.toString(), "utf-8");
		return soapResultData.toString();
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
}
