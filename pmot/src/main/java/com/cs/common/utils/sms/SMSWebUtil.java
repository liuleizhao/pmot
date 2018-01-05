package com.cs.common.utils.sms;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.cs.common.utils.DESCorder;
import com.cs.common.utils.XmlHelper;
/**
 * @ClassName:    ZBWebUtil.java
 * @Description:  短信接口
 * @date          2016-1-22  上午10:41:47
 */
public class SMSWebUtil {
	private static String SMSURL = "http://192.168.2.146:9080/xxfbpt/services/xxfbptservice";
//	private static String SMSURL = "http://app.stc.gov.cn:9083/xxfbpt/services/xxfbptservice";
	/** 命名空间 */
	private static String namespace = "http://webservice.xxfbpt.com/";
	private static String methodName = "xxptSchuding";
	

	private static String USER_ID = "CGYY";//
	private static String USER_PWD = "CGYY@YT";//
	private static String JKID = "sms";//
	private static String KEY = "MCG7GqQgHae9zkoKQRxGITmE";//
	
	public static Call getCall() {
		Call call = null;
		try {
			Service service = new Service();
			 call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(SMSWebUtil.SMSURL));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(namespace + methodName);
			call.setOperationName(new QName(namespace, methodName));// 访问地址
			call.addParameter(new QName(namespace, "userid"), XMLType.XSD_STRING,ParameterMode.IN);
			call.addParameter(new QName(namespace, "userpwd"),XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(namespace, "jkid"), XMLType.XSD_STRING,ParameterMode.IN);
			call.addParameter(new QName(namespace, "srcs"),XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型
			call.setTimeout(20000);  //20秒调用时间,20000 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return call;
	}
	
	

	
	/**
	 * @Description:  发送短信 
	 * @return        String
	 * @date          2016-1-28  上午9:57:26
	 */
	public static Map<String,String> callWSForSMS(String mobile,String message) {
		Call call = getCall();
		StringBuffer rqStr = new StringBuffer("");
		rqStr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		rqStr.append("<request>").append("<userZh>").append("iaspec")
				.append("</userZh>").append("<userMy>").append("iaspec888")
				.append("</userMy>").append("<mobile>").append(mobile)
				.append("</mobile>").append("<content>").append(message)
				.append("</content>").append("</request>");
		String responseXml = "";
		Map<String,String> result = new HashMap<String, String>();
		String code = "0";
		String msg = "无法调用短信接口，请稍后重试或联系短信接口维护人员";
		try {
			String rqString = new String(rqStr.toString().getBytes("UTF-8"));
			String srcs = DESCorder.encryptModeToString(rqString,KEY);
			 responseXml = (String) call.invoke(new Object[] { USER_ID, USER_PWD,JKID , srcs });
			 code = XmlHelper.getValue("code", responseXml);
			 msg = XmlHelper.getValue("msg", responseXml);
			 if(!"0000".equals(code)){
				 result.put("code",code);
				 result.put("msg", msg);
			 }else {
				 responseXml = DESCorder.decryptMode(msg,"MCG7GqQgHae9zkoKQRxGITmE","UTF-8");
				 code = XmlHelper.getValue("code", responseXml);
				 msg = XmlHelper.getValue("msg", responseXml);
				 result.put("code",code);
				 result.put("msg", msg);
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
			 result.put("code","-99");
			 result.put("msg", msg);
		}
		
		return result;
	}
	/**
	 * @Description:  发送短信 
	 * @return        String
	 * @date          2016-1-28  上午9:57:26
	 */
	public static Map<String,Object> callWSForSMSTest(String mobile,String message) {
		Date startDate = new Date();
		Call call = getCall();
		StringBuffer rqStr = new StringBuffer("");
		rqStr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		rqStr.append("<request>").append("<userZh>").append("iaspec")
				.append("</userZh>").append("<userMy>").append("iaspec888")
				.append("</userMy>").append("<mobile>").append(mobile)
				.append("</mobile>").append("<content>").append(message)
				.append("</content>").append("</request>");
		String responseXml = "";
		Map<String,Object> result = new HashMap<String, Object>();
		String code = "0";
		String msg = "";
		try {
			String rqString = new String(rqStr.toString().getBytes("UTF-8"));
			String srcs = DESCorder.encryptModeToString(rqString,KEY);
			 responseXml = (String) call.invoke(new Object[] { USER_ID, USER_PWD,JKID , srcs });
			 code = XmlHelper.getValue("code", responseXml);
			 msg = XmlHelper.getValue("msg", responseXml);
			 if(!"0000".equals(code)){
				 result.put("code",code);
				 result.put("msg", msg);
			 }else {
				 responseXml = DESCorder.decryptMode(msg,"MCG7GqQgHae9zkoKQRxGITmE","UTF-8");
				 code = XmlHelper.getValue("code", responseXml);
				 msg = XmlHelper.getValue("msg", responseXml);
				 result.put("code",code);
				 result.put("msg", msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseXml = e.getMessage();
			 result.put("code","-99");
			 result.put("msg", responseXml);
		}
		Date endDate = new Date();
		result.put("date",(endDate.getTime() -startDate.getTime()));
		return result;
	}
}
