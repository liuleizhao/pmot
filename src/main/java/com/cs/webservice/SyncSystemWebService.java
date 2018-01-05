package com.cs.webservice;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cs.common.entityenum.InterfaceControlState;
import com.cs.common.exception.InvalidXMLArgumentException;
import com.cs.common.utils.IpUtil;
import com.cs.common.utils.webservice.ResultCode;
import com.cs.common.utils.webservice.WebServiceXmlUtil;
import com.cs.common.utils.xml.XmlBeanUtil;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.init.InitData;
import com.cs.mvc.util.SpringContextHolder;
import com.cs.webservice.dto.XmlHeadVo;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterfaceControlDetail;
import com.cs.webservice.entity.InterfaceControlGeneral;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.service.InterfaceControlDetailService;
import com.cs.webservice.service.InterfaceControlGeneralService;

/**
 * 同步系统WebService
 * @ClassName:    SyncSystemWebService
 * @Description:  
 * @Author        succ
 * @date          2017-11-9  下午12:20:35
 */
public class SyncSystemWebService {
	public String uniformAccess(String xml) throws UnsupportedEncodingException{
		//1、校验jkid是否为空，接口序列号是否为空
		//2、根据IP、接口序列号验证权限
		//3、根据jkid查询相应的方法及参数，校验参数是否正确
		//4、利用反射机制调用相应的方法
		try{
			xml = URLDecoder.decode(xml, "utf-8");
			XmlHeadVo xmlHeadVo = WebServiceXmlUtil.getHeadVo(xml);
			String jkid = xmlHeadVo.getJkid();//接口ID
			String jkxlh = xmlHeadVo.getJkxlh();//接口序列号
			if(StringUtils.isBlank(jkid) || StringUtils.isBlank(jkxlh)){
				return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：请传入接口id、接口序列号参数");
			}
			
			//验证客户端访问WebService权限 		
			String ip = getRequestIp();
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
			
			InterfaceInfo interfaceInfo = InitData.getInterfaceInfo(jkid);
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
			
			
			String className = interfaceInfo.getClassName();
			Class<?> interfaceInfoType = Class.forName(className);
			List<InterParamRelation> params = interfaceInfo.getParams();
			Map<String,Object> validateXmlResult = validateQueryXml(xml,params);
			Method interfaceMethod = interfaceInfoType.getDeclaredMethod(interfaceInfo.getMethodName(),(Class<?>[])validateXmlResult.get("paramTypes"));
			Object target = SpringContextHolder.getBean(interfaceInfoType);
			String responseXml = (String)(interfaceMethod.invoke(target,(Object[])validateXmlResult.get("paramValues"))); 
			System.out.println("---------------------------------------------");
			System.out.println(responseXml);
			System.out.println("---------------------------------------------");
			return responseXml;
		}catch(DocumentException e){
			return WebServiceXmlUtil.buildResponseXml(ResultCode.FAIL, "服务器端提示信息：非标准xml格式，无法解析：【"+e.getMessage()+"】");
		}catch(InvalidXMLArgumentException e){
			e.printStackTrace();
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR,e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return WebServiceXmlUtil.buildResponseXml(ResultCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 校验查询条件XML，并返回相应的数据
	 * @param QueryXmlDoc
	 * @param params
	 * @return
	 * @throws DocumentException 
	 * @throws InvalidXMLArgumentException 
	 * @throws ClassNotFoundException 
	 * @Author        succ
	 * @date 2017-10-28 下午12:22:14
	 */
	private Map<String,Object> validateQueryXml(String QueryXmlDoc,List<InterParamRelation> params) throws DocumentException, InvalidXMLArgumentException, ClassNotFoundException{
		Map<String,Object> result = new HashMap<String,Object>();
		Object[] paramValues = null; //参数值数组
		Class<?>[] paramTypes = null;//参数类型数组
		
		Document doc = DocumentHelper.parseText(QueryXmlDoc);
		Element root = doc.getRootElement();
		Element bodyEle = root.element("body");
		if(CollectionUtils.isNotEmpty(params)){
			if(bodyEle == null){
				throw new InvalidXMLArgumentException("服务器端提示信息：非标准xml格式，【body】元素不能为空");
			}
			paramValues = new Object[params.size()];
			paramTypes = new Class<?>[params.size()];
			
			for(int i=0; i<params.size(); i++){
				InterParamRelation param = params.get(i);
				String className = param.getParameter().getType();
				String paramName = param.getParameter().getName();
				
				paramTypes[i] = Class.forName(className);
				if(className.startsWith("java.lang")){
					Element paramEle = bodyEle.element(paramName);
					String paramValue = bodyEle.elementText(paramName);
					//校验非空参数
					if(param.getNotNull()==1 && paramEle==null){
						throw new InvalidXMLArgumentException("参数不能为空："+paramName);
					}
					paramValues[i] = StringUtils.isNotBlank(paramValue) ? paramValue : paramEle.asXML();
				}else{
					Element objElt = bodyEle.element(paramName);
					
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
		
		result.put("paramValues", paramValues);
		result.put("paramTypes", paramTypes);
		return result;
	}
	
	
	/**
	 * 获得客户端IP
	 * @return
	 */
	private String getRequestIp(){
		String ip = StringUtils.EMPTY;
	    MessageContext  mc  =  MessageContext.getCurrentContext();
	    HttpServletRequest  request  =  (HttpServletRequest)  mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
	    if(request !=null){
	    	ip = IpUtil.getClientIp(request);
	    }
		return ip;
	}
	
	
	private InterfaceControlDetailService controlDetailService;
	private InterfaceControlGeneralService controlGeneralService;
	
	public SyncSystemWebService(){
        controlDetailService = SpringContextHolder.getBean(InterfaceControlDetailService.class);
        controlGeneralService = SpringContextHolder.getBean(InterfaceControlGeneralService.class);
	}
}
