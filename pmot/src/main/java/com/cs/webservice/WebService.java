package com.cs.webservice;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URLDecoder;
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

import com.cs.common.entityenum.InterfaceControlState;
import com.cs.common.entityenum.InterfaceInvokeType;
import com.cs.common.entityenum.Recordable;
import com.cs.common.exception.InvalidXMLArgumentException;
import com.cs.common.utils.ConvertUtils;
import com.cs.common.utils.database.Transformer;
import com.cs.common.utils.database.impl.DefaultTransformer;
import com.cs.common.utils.webservice.ResultCode;
import com.cs.common.utils.webservice.WebServiceIpUtil;
import com.cs.common.utils.webservice.WebServiceXmlUtil;
import com.cs.common.utils.xml.XmlBeanUtil;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.init.InitData;
import com.cs.mvc.util.SpringContextHolder;
import com.cs.photo.service.PhotoInfoService;
import com.cs.webservice.dto.SynchronizeEntity;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterfaceControlDetail;
import com.cs.webservice.entity.InterfaceControlGeneral;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.entity.InterfaceLog;
import com.cs.webservice.service.InterfaceControlDetailService;
import com.cs.webservice.service.InterfaceControlGeneralService;
import com.cs.webservice.service.InterfaceInfoService;
import com.cs.webservice.service.InterfaceLogService;

public class WebService{
	
	private PhotoInfoService photoInfoService;
	private InterfaceInfoService interfaceInfoService;
	private InterfaceLogService interfaceLogService;
	private DataSource dataSource;
	private InterfaceControlDetailService controlDetailService;
	private InterfaceControlGeneralService controlGeneralService;
	
	public WebService(){
		photoInfoService = SpringContextHolder.getBean(PhotoInfoService.class); 
        interfaceInfoService = SpringContextHolder.getBean(InterfaceInfoService.class);
        interfaceLogService = SpringContextHolder.getBean(InterfaceLogService.class);
        dataSource = SpringContextHolder.getBean("dataSource", DataSource.class);
        controlDetailService = SpringContextHolder.getBean(InterfaceControlDetailService.class);
        controlGeneralService = SpringContextHolder.getBean(InterfaceControlGeneralService.class);
	}
	
	/**
	 * WebService接口-获取照片
	 * @param argsXML
	 * @return
	 * @Author        succ
	 * @date 2017-10-26 上午10:10:55
	 */
	public String getPhoto(String argsXML) {
		return photoInfoService.getPhotoByXML(argsXML);
	}
	
	/**
	 * WebService查询接口
	 * @param xtlb
	 * @param jkxlh
	 * @param jkid
	 * @param QueryXmlDoc
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-10-26 上午11:16:22
	 */
	public String query(String QueryXmlDoc) throws Exception{
		String ip = WebServiceIpUtil.getRequestIp();
		InterfaceLog latestLog = interfaceLogService.findLatestByIp(ip);
		Date requestDate  = new Date();//请求时间
		//接口调用频率限制
		int jkpl = 10000;
		String jkplStr = InitData.getGlobalValue("JKPL");
		try{
			if(jkplStr != null){
				jkpl = Integer.parseInt(jkplStr);
			}
		}catch(NumberFormatException e){
		}
		if(latestLog!=null && requestDate.getTime()-latestLog.getRequestDate().getTime()<=jkpl){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：操作过于频繁，请稍后重试");
		}
		String xtlb =  null;
		String jkid = null;
		String jkxlh = null;
		InterfaceInfo interfaceInfo = null;
		
		//解析HEAD内容
		try {
			QueryXmlDoc = URLDecoder.decode(QueryXmlDoc, "utf-8");
			Document document = DocumentHelper.parseText(QueryXmlDoc);
			Element root = document.getRootElement();
			Element headElt = (Element) root.selectSingleNode("//head"); // 查找根元素下的第一个head
			xtlb = headElt.elementTextTrim("xtlb");
			jkid = headElt.elementTextTrim("jkid");
			jkxlh = headElt.elementTextTrim("jkxlh");
			
		} catch (Exception e) {// 无法解析为DOM则直接返回失败
			e.printStackTrace();
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：非标准xml格式，无法解析：【"+e.getMessage()+"】");
 		}
		
		try{
		//验证非空参数
		if(StringUtils.isBlank(ip)|| StringUtils.isBlank(jkid) ||StringUtils.isBlank(jkxlh)){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：请传入接口id、接口序列号参数");
		}
		
		//验证客户端访问WebService权限 		
		SqlCondition generalSqlCondition = new SqlCondition();
		generalSqlCondition.addLikeCriterion("ips like ", "$"+ip+"$");
		generalSqlCondition.addSingleNotNullCriterion("serial_number = ", jkxlh);
		InterfaceControlGeneral general = controlGeneralService.findUniqueByCondition(generalSqlCondition);
		
		if(general == null){//找不到对应记录
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：当前IP和接口序列号无接口访问权限");
		}
		if(!InterfaceControlState.SYZ.equals(general.getState())){//非使用中状态
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：当前IP和接口序列号对应接口访问权限已失效");

		}
		
		//验证接口是否存在
//		SqlCondition interfaceSqlCondition = new SqlCondition();
//		interfaceSqlCondition.addSingleNotNullCriterion("jkid = ", jkid);
//		interfaceInfo = interfaceInfoService.findUniqueByCondition(interfaceSqlCondition);
		interfaceInfo = InitData.getInterfaceInfo(jkid);
		
		
		if(interfaceInfo == null){//找不到对应记录
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：接口id错误，未找到接口【"+jkid+"】");
		}

		//验证客户端访问具体接口的权限
		SqlCondition detailSqlCondition = new SqlCondition();
		detailSqlCondition.addSingleNotNullCriterion("general_id = ", general.getId());
		detailSqlCondition.addSingleNotNullCriterion("interface_id = ", interfaceInfo.getId());
		InterfaceControlDetail detail = controlDetailService.findUniqueByCondition(detailSqlCondition);

		if(detail == null){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：无接口【"+jkid+"】的访问权限");
		}
		
		if(!InterfaceControlState.SYZ.equals(detail.getState())){//非使用中状态
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：当前接口【"+jkid+"】的访问权限已失效");

		}
		
		}catch(Exception e){
			e.printStackTrace();
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR);
			
		}
		
		String responseXml = null;
		try{
			String className = interfaceInfo.getClassName();
			Class<?> interfaceInfoType = Class.forName(className);
//			List<InterParamRelation> params = interfaceInfoService.findParameters(interfaceInfo.getId());
			List<InterParamRelation> params = interfaceInfo.getParams();
			Map<String,Object> validateXmlResult = validateQueryXml(QueryXmlDoc,params);
			ResultCode validateResult = (ResultCode)validateXmlResult.get("code");
			if(validateResult!=ResultCode.SUCCESS){
				return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL,(String)validateXmlResult.get("message"));
			}
			Method interfaceMethod = interfaceInfoType.getDeclaredMethod(interfaceInfo.getMethodName(),(Class<?>[])validateXmlResult.get("paramTypes"));
			Object target = SpringContextHolder.getBean(interfaceInfoType);
			responseXml = (String)(interfaceMethod.invoke(target,(Object[])validateXmlResult.get("paramValues"))); 
		}catch(Exception e){
			responseXml = WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR,e.getMessage());
			e.printStackTrace();
			//TOOD  logback
 		}
		
		try{
			// 验证是否需要记录日志
			if (Recordable.YES.equals(interfaceInfo.getRecordable())) {
				InterfaceLog log = new InterfaceLog();
				Date responseDate = new Date();
				log.setJkid(jkid);
				log.setIp(ip);
				log.setRequestDate(requestDate);
				log.setRequestXml(QueryXmlDoc);
				log.setResponseDate(responseDate);
				log.setResponseXml(ConvertUtils.decodeUTF8Xml(responseXml));
				log.setRunTime((new Long((responseDate.getTime() - requestDate.getTime()))).intValue());
				log.setInvokeType(InterfaceInvokeType.STATION);
				interfaceLogService.insert(log);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return responseXml;
		
	}
	
	/**
	 * WebService写入接口
	 * @param xtlb
	 * @param jkxlh
	 * @param jkid
	 * @param WriteXmlDoc
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-10-28 下午4:25:13
	 */
	public String write(String xtlb, String jkxlh, String jkid, String WriteXmlDoc) throws Exception {
		// 对请求报文做处理
		List<SynchronizeEntity> list = null;
		try {
			WriteXmlDoc = URLDecoder.decode(WriteXmlDoc, "utf-8");
			System.out.println(WriteXmlDoc);
			list = getSynchronizeEntityFromXml(WriteXmlDoc);
		} catch (Exception e) {
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL,"XML格式错误");
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
					} catch (SQLIntegrityConstraintViolationException e1) {
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
	
	/**
	 * 从写入XML中获取同步数据实体
	 * @param synXml
	 * @return
	 * @throws DocumentException
	 * @Author        succ
	 * @date 2017-10-28 下午4:26:24
	 */
	@SuppressWarnings("unchecked")
	private List<SynchronizeEntity> getSynchronizeEntityFromXml(String WriteXmlDoc) throws DocumentException{
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
	 * 校验查询条件XML，并返回相应的数据
	 * @param QueryXmlDoc
	 * @param params
	 * @return
	 * @Author        succ
	 * @date 2017-10-28 下午12:22:14
	 */
	private Map<String,Object> validateQueryXml(String QueryXmlDoc,List<InterParamRelation> params){
		Map<String,Object> result = new HashMap<String,Object>();
		ResultCode code = ResultCode.SUCCESS;
		String message = "校验成功";
		Object[] paramValues = null; //参数值数组
		Class<?>[] paramTypes = null;//参数类型数组
		
		Document doc;
		try {
			doc = DocumentHelper.parseText(QueryXmlDoc);
			Element root = doc.getRootElement();
			Element queryCondition = root.element("QueryCondition");
			if(CollectionUtils.isNotEmpty(params)){
				paramValues = new Object[params.size()];
				paramTypes = new Class<?>[params.size()];
				
				for(int i=0; i<params.size(); i++){
					InterParamRelation param = params.get(i);
					String className = param.getParameter().getType();
					String paramName = param.getParameter().getName();
					
					paramTypes[i] = Class.forName(className);
					if(className.startsWith("java.lang")){
						String paramValue = queryCondition.elementText(paramName);
						//校验非空参数
						if(param.getNotNull()==1 && StringUtils.isBlank(paramValue)){
							throw new InvalidXMLArgumentException("参数不能为空："+paramName);
						}
						paramValues[i] = paramValue;
					}else{
						
						Element objElt = queryCondition.element(paramName);
						
						//实体类型参数，需要判断实体中哪些属性是必须的
						String requiredAttrs = param.getRequiredAttrs();
						if(StringUtils.isNotBlank(requiredAttrs)){
							String[] arr = requiredAttrs.split(",");
							for(String attrName : arr){
								if(StringUtils.isBlank(objElt.elementText(attrName))){
									throw new InvalidXMLArgumentException("参数不能为空："+attrName);
								}
							}
						}
						
						String simpleName = Class.forName(className).getSimpleName();
						StringBuilder sb = new StringBuilder();
						sb.append(simpleName.substring(0, 1).toLowerCase());
						sb.append(simpleName.substring(1, simpleName.length()));
						String tmpXml = objElt.asXML().replaceAll(paramName, sb.toString());
						paramValues[i] = XmlBeanUtil.xmlToBean(tmpXml, Class.forName(className));
					}
				}
			}else{
				paramValues = new Object[0];
				paramTypes = new Class<?>[0];
			}
		} catch (InvalidXMLArgumentException e) {
			code = ResultCode.FAIL;
			message = e.getMessage();
		} catch (Exception e) {
			code = ResultCode.FAIL;
			message = StringUtils.isBlank(e.getMessage()) ? "XML格式错误" : e.getMessage();
		}
		
		result.put("code", code);
		result.put("message", message);
		result.put("paramValues", paramValues);
		result.put("paramTypes", paramTypes);
		return result;
	}
	
	public static void main(String[] args) throws DocumentException {
		String str = "<root><tables><table><name>scc</name><objs><obj><id>1</id><name>1name</name><age>1age</age></obj><obj><name>2name</name><id>2</id><age>2age</age></obj>";
		str += "</objs></table><table><name>scc2</name><objs><obj><mobile>15270885226</mobile><platNumber>粤B123</platNumber></obj></objs></table></tables></root>";
		
		List<SynchronizeEntity> list = new WebService().getSynchronizeEntityFromXml(str);
		for(SynchronizeEntity entity : list){
			System.out.println(entity.getTableName());
			System.out.println(entity.getColumns());
			System.out.println(entity.getValues());
			System.out.println("--------------------------------------------");
		}
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
