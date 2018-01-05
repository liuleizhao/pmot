package com.cs.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;



public class XmlHelper {

	/**默认编码 */
	static final String default_encoding = "UTF-8";


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

	
	/**
	 * @Description:  获得xml节点中的数据
	 * @param colName
	 * @param xmlDoc
	 * @return        String
	 * @date          2016-1-29  下午12:02:49
	 */
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
	
	/**
	 * @Description:  获得xml节点中的数据，去除[CDATA[ 非正常文本
	 * @param colName
	 * @param xmlDoc
	 * @return        String
	 * @date          2016-1-29  下午12:03:23
	 */
	public static String getText(String colName, String xmlDoc) {
		int startIndex = -1;
		int endIndex = -1;
		startIndex = xmlDoc.indexOf("<" + colName + "><![CDATA[");
		endIndex = xmlDoc.indexOf("]]></" + colName + ">");
		if (startIndex >= 0 && endIndex >= 0) {
			return xmlDoc
					.substring(startIndex + colName.length() + 11, endIndex);
		} else {
			return null;
		}
	}
	
	public static void main(String args[]) {
//		String xmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>"
//				+ "\n<QueryCondition>\n<code>" + "1" + "</code>\n<sfzmhm>"
//				+ "1234567" + "</sfzmhm>\n<msg>" + "几点回家圣诞节是肯定"
//				+ "</msg>\n</QueryCondition>\n</root>";
//		String xml1 = encodeXml(xmlDoc);
//		System.out.println(xml1);
//		String xml2 = decodeUTF8Xml(xml1);
//		System.out.println(xml2);
//		System.out.println(getValue("msg",xml2));
	}

}
