package com.cs.common.utils.webservice;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cs.appoint.entity.VehIsInfo;
import com.cs.webservice.dto.XmlHeadVo;
import com.thoughtworks.xstream.XStream;

/**
 * WebService专用XML工具类
 * @ClassName:    WebServiceXmlUtil
 * @Description:  
 * @Author        succ
 * @date          2017-10-31  下午4:46:08
 */
public class WebServiceXmlUtil {
	
	private static final XStream xstream = new XStream();
	
	public static String buildResponseXml(ResultCode resultCode){
		return buildResponseXml(resultCode,resultCode.getDescription());
	}
	
	public static String buildResponseXml(ResultCode result,String message){
		return buildResponseXml(result,message,null);
	}
	
	public static String buildResponseXml(ResultCode result,String message,String bodyXML){
		StringBuffer xmlDoc = new StringBuffer();
		xmlDoc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n<head>\n\t<code>"+result.getCode()+"</code>\n\t");
		xmlDoc.append("<message>"+message+"</message>\n\t</head>\n");
		if(StringUtils.isNotBlank(bodyXML)){
			xmlDoc.append("<body>\n"+bodyXML+"</body>");
		}
		xmlDoc.append("\n</root>");
		System.out.println(xmlDoc.toString());
		//方便测试，注释掉以下代码，发布时需要放开注释
		try {
			return URLEncoder.encode(xmlDoc.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("---------------------构建返回报文出错---------------------------");
			return xmlDoc.toString();
		}
	}
	
	public static <T> String buildXmlFromList(List<?> objs,Class<?> clz) throws Exception{
		StringBuffer xmlDoc = new StringBuffer();
		for(int i=0; i<objs.size(); i++){
			xmlDoc.append("<vehispara id=\""+i+"\">\n");
			BeanInfo beanInfo = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor prop : props){
				String fieldName = prop.getName();
				Object fieldValue = prop.getReadMethod().invoke(objs.get(i));
				if(fieldName.equals("class") || fieldValue == null){
					continue;
				}
				xmlDoc.append("\t<"+fieldName+">");
				if(fieldValue.getClass().isArray()){
					xmlDoc.append(clearXstreamTag(xstream.toXML(fieldValue)));
				}else{
					xmlDoc.append(fieldValue.toString());
				}
				xmlDoc.append("</"+fieldName+">\n");
			}
			xmlDoc.append("</vehispara>\n");
		}
		return xmlDoc.toString();
	}
	
	/**
	 * 获取VehIsInfo的基本信息报文
	 * @param vehIsInfo
	 * @return
	 * @throws Exception
	 */
	public static String buildBaseInfo(VehIsInfo vehIsInfo) throws Exception{
		StringBuffer xmlDoc = new StringBuffer();
		xmlDoc.append("<baseinfo>\n");
		BeanInfo beanInfo = Introspector.getBeanInfo(VehIsInfo.class);
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor prop : props){
			String fieldName = prop.getName();
			Object fieldValue = prop.getReadMethod().invoke(vehIsInfo);
			if(fieldName.equals("class") || fieldValue == null){
				continue;
			}
			xmlDoc.append("\t<"+fieldName+">");
			if(fieldValue.getClass().isArray()){
				xmlDoc.append(clearXstreamTag(xstream.toXML(fieldValue)));
			}else{
				xmlDoc.append(fieldValue.toString());
			}
			xmlDoc.append("</"+fieldName+">\n");
		}
		xmlDoc.append("</baseinfo>\n");
		return xmlDoc.toString();
	}
	
	private static String clearXstreamTag(String xml){
		return xml
				.replaceAll("<string>", "").replaceAll("</string>", "").replaceAll("<string-array>", "").replaceAll("</string-array>", "")
				.replaceAll("<double>", "").replaceAll("</double>", "").replaceAll("<double-array>", "").replaceAll("</double-array>", "")
				.replaceAll("<float>", "").replaceAll("</float>", "").replaceAll("<float-array>", "").replaceAll("</float-array>","")
				.replaceAll("<byte>", "").replaceAll("</byte>", "").replaceAll("<byte-array>", "").replaceAll("</byte-array>", "")
				.replaceAll("<long>", "").replaceAll("</long>", "").replaceAll("<long-array>", "").replaceAll("</long-array>", "")
				.replaceAll("<int>", "").replaceAll("</int>","").replaceAll("<int-array>","").replaceAll("</int-array>","");
	}
	
	/**
	 * 封装Head部分XML
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @Author        succ
	 * @date 2017-11-2 上午11:43:45
	 */
	public static XmlHeadVo getHeadVo(String xml) throws DocumentException{
		XmlHeadVo xmlHeadVo = null;
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		Element headEle = root.element("head");
		xmlHeadVo = new XmlHeadVo();
		xmlHeadVo.setJkid(headEle.elementTextTrim("jkid"));
		xmlHeadVo.setJkxlh(headEle.elementTextTrim("jkxlh"));
		xmlHeadVo.setXtlb(headEle.elementTextTrim("xtlb"));
		return xmlHeadVo;
	}
	
	public static void main(String[] args) {
		byte[] b = new byte[10];
		for(int i=0; i<10; i++){
			b[i] = (byte)i;
		}
		System.out.println(b);
		System.out.println(b.getClass().isArray());
		System.out.println(Arrays.toString(b));
	}
	
	/**
	 * 获取标签中的内容
	 * @param tagName
	 * @param xmlDoc
	 * @return
	 */
	public static String getTagValue(String tagName, String xmlDoc) {
		int startIndex = -1;
		int endIndex = -1;
		startIndex = xmlDoc.indexOf("<" + tagName + ">");
		endIndex = xmlDoc.indexOf("</" + tagName + ">");
		if (startIndex >= 0 && endIndex >= 0) {
			return xmlDoc
					.substring(startIndex + tagName.length() + 2, endIndex);
		} else {
			return null;
		}
	}
}
