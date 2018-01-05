package com.cs.common.utils.webservice;

import java.util.List;

import com.thoughtworks.xstream.XStream;

public class XmlUtil {
	private static final XmlUtil xmlUtil = new XmlUtil();

	private XStream xstream;

	private XmlUtil() {
		xstream = new XStream();
	}

	public static XmlUtil getInstance() {
		return xmlUtil;
	}

	public String getSuccessResponseXml(Object result) {
		return getResponseXml( ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDescription(), result);
	}
	
	public String getSuccessResponseXml(Object result ,String msg) {
		return getResponseXml( ResultCode.SUCCESS.getCode(), msg , result);
	}

	public String getErrorResponseXml(ResultCode resultCode ,String msg){
		return  getErrorResponseXml( resultCode.getCode() ,resultCode.getDescription() , msg);
	}
	
	public String getErrorResponseXml(ResultCode resultCode){
		return  getErrorResponseXml( resultCode.getCode() , resultCode.getDescription() ,"");
	}
	
	public String getErrorResponseXml(String code ,String msg , String result) {
		return getResponseXml(code, msg, result);
	};

	private String getResponseXml(String code, String msg, Object result) {
		ResponseMessage responseMsg = new ResponseMessage(code, msg, result);
		//包重命名、去掉当前包名及VO包名 
        xstream.aliasPackage("", "com.rich.admin.common.utils.ws");
        xstream.aliasPackage("", "com.rich.admin.vo.ws");
        xstream.aliasPackage("", "com.rich.admin.vo.argument");
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xstream.toXML(responseMsg);  
		xmlStr = xmlStr.replace("class=\"sql-timestamp\"", ""); //TIMESTAMP类型的字段有这个属性
		if(result != null){
			//Class clazz = result.getClass();
			if(result instanceof String){
				xmlStr = xmlStr.replace(" class=\"string\"","");//去除list 以后再区分类型 .
			}else if(result instanceof List){
				xmlStr = xmlStr.replace(" class=\"list\"","");//去除list 以后再区分类型 .
			}
		}
		
//		try {
//			URLEncoder.encode(xmlStr, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return xmlStr;
	};

}
