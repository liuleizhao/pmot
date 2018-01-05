package com.cs.common.utils;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class RequestUtils {

	/** 调用URL */
	public static String url = "http://190.205.0.2:9080/pnweb/services/TmriOutAccess";

	/** 命名空间 */
	private static String namespace = "http://tempuri.org/";

	public static String queryObjectOut(String xtlb, String jkxlh, String jkid,
			String QueryXmlDoc) throws Exception {
		String responseXml = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + ConvertUtils.query);
			call.setOperationName(new QName(namespace, ConvertUtils.query));// 访问地址
			call.addParameter(new QName(namespace, "xtlb"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "jkxlh"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(namespace, "jkid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "UTF8XmlDoc"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型
			responseXml = (String) call.invoke(new Object[] { xtlb, jkxlh,
					jkid , QueryXmlDoc });
		} catch (Exception e) {
			e.printStackTrace();
			responseXml=e.getMessage();
		}
		
		return responseXml;
	}
	
	
	
	public static String synchronizdTime(String xtlb, String jkxlh, String jkid,
			String QueryXmlDoc) throws Exception {
		String responseXml = null;
			try {
				Service service = new Service();
				Call call = (Call) service.createCall();
				call.setTargetEndpointAddress(new URL(url));
				call.setUseSOAPAction(true);
				call.setSOAPActionURI(namespace + ConvertUtils.query);
				call.setOperationName(new QName(namespace, ConvertUtils.query));// 访问地址
				call.addParameter(new QName(namespace, "xtlb"),
						XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter(new QName(namespace, "jkxlh"),
						XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter(new QName(namespace, "jkid"),
						XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter(new QName(namespace, "UTF8XmlDoc"),
						XMLType.XSD_STRING, ParameterMode.IN);
				call.setReturnType(XMLType.XSD_STRING);// 返回值类型
				responseXml = (String) call.invoke(new Object[] { xtlb, jkxlh,
						jkid, QueryXmlDoc });
			} catch (Exception e) {
				e.printStackTrace();
				responseXml=e.getMessage();
			}
		return responseXml;
	}

	public static String writeObjectOut(String xtlb, String jkxlh, String jkid,
			String writeXmlDoc) throws Exception {
		String responseXml = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + ConvertUtils.query);
			call.setOperationName(new QName(namespace, ConvertUtils.write));// 访问地址
			call.addParameter(new QName(namespace, "xtlb"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "jkxlh"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(namespace, "jkid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "UTF8XmlDoc"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型
			responseXml = (String) call.invoke(new Object[] { "18", jkxlh,
					jkid, writeXmlDoc });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseXml;
	}
	
	public static String queryObject(String url,String interf, String QueryXmlDoc) throws Exception {
		String responseXml = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + interf);
			call.setOperationName(new QName(namespace, interf));// 访问地址
			call.addParameter(new QName(namespace, "strXml"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型
			responseXml = (String) call.invoke(new Object[] { QueryXmlDoc });
		} catch (Exception e) {
			e.printStackTrace();
			responseXml=e.getMessage();
		}
		return responseXml;
	}
}
