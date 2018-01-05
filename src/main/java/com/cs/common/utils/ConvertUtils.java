 package com.cs.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.beanutils.BeanUtils;





public class ConvertUtils {

	/** 监管平台默认编码 */
	static final String default_encoding = "UTF-8";

	/** 调用URL */
	public static String url = "http://100.101.0.2:9080/pnweb/services/TmriOutAccess";

	/** 命名空间 */
	public static String namespace = "http://tempuri.org/";

	/** 查询接口 */
	public static String query = "queryObjectOut";

	/** 写入接口 */
	public static String write = "queryObjectOut";

	/**
	 * 解码-->UTF-8 到unicode
	 * 
	 * @param xmlDoc
	 * @return
	 */
	public static String decodeUTF8Xml(String xmlDoc) {
		try {
			return URLDecoder.decode(xmlDoc, default_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 编码-->unicode 到 UTF-8
	 * 
	 * @param xmlDoc
	 * @param encoding
	 * @return
	 */
	public static String encodeXml(String xmlDoc) {
		try {
			return URLEncoder.encode(xmlDoc, default_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return xmlDoc;
		}
	}

	public static Object Xml2Bean(String xmlDoc, Object bean, Class clazz,
			List<String> exceptProperties) {
		xmlDoc = decodeUTF8Xml(xmlDoc);// 解码

		Field[] fields = clazz.getDeclaredFields();
		if (bean == null) {
			try {
				bean = clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Field field : fields) {
			String fieldName = field.getName();
			if (!exceptProperties.contains(fieldName)) {
				String fieldValue = getValue(fieldName, xmlDoc);
				if (fieldValue!=null&&fieldValue!="") {
					try {
						BeanUtils.setProperty(bean, fieldName, fieldValue);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		return bean;
	}
	
	public static String Bean2Xml(Object bean, Class clazz,
			List<String> exceptProperties) throws Exception {
		StringBuffer xmlDoc = new StringBuffer();
		xmlDoc.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n").append("<root>\n").append("<vehispara>\n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (!exceptProperties.contains(fieldName)) {
				String fieldValue = BeanUtils.getProperty(bean, fieldName);
				if(fieldValue!=null&&fieldValue!=""){
					xmlDoc.append("<"+fieldName+">"+BeanUtils.getProperty(bean, fieldName)+"</"+fieldName+">\n");
				}else{
					xmlDoc.append("<"+fieldName+"></"+fieldName+">\n");
				}
			}
		}
		xmlDoc.append("</vehispara>\n").append("</root>");
		return xmlDoc.toString();
	}

	public static String getValue(String colName, String xmlDoc) {
		int startIndex = -1;
		int endIndex = -1;
		startIndex = xmlDoc.indexOf("<" + colName + ">");
		endIndex = xmlDoc.indexOf("</" + colName + ">");
		if (startIndex >= 0 && endIndex >= 0) {
			return xmlDoc
					.substring(startIndex + colName.length() + 2, endIndex);
		} else {
			return null;
		}
	}
	
	public static void main(String args[]) {
		Class clazz = null;
		try {
			clazz = Class.forName("com.innerbook.entity.loginsystem.VehIsInfo");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ajjgbh = "";
		String xmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>"
				+ "\n<QueryCondition>\n<hphm>粤BBQ161</hphm>\n<hpzl>02</hpzl>\n<clsbdh>"
				+ "0286</clsbdh>\n<jyjgbh>4400000142</jyjgbh>\n</QueryCondition>\n</root>";

	//	xmlDoc =  encodeXml(xmlDoc);
		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + query);
			call.setOperationName(new QName(namespace, query));// 访问地址
			call.addParameter(new QName(namespace, "xtlb"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "jkxlh"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(namespace, "jkid"), XMLType.XSD_STRING,
					ParameterMode.IN);
			call.addParameter(new QName(namespace, "UTF8XmlDoc"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型
			String jkxlh = "781A0909020517040015FBDFEBE8F3E2FD9EF39689C2E1F7F3C1D19EB8C7B1DBCEF1CFB5CDB3";
			String responseXml = (String) call.invoke(new Object[] { "18",
					jkxlh, "18C49", xmlDoc });
			responseXml = decodeUTF8Xml(responseXml);
			System.out.println(responseXml);
			System.out.println(new Date());
		//	VehIsInfo vehisInfo  = (VehIsInfo) ConvertUtils.Xml2Bean(responseXml, null, clazz, new ArrayList<String>());

		//	System.out.println(vehisInfo);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 调用方法
		Xml2Bean(xmlDoc, null, clazz, new ArrayList<String>());
	}

}
