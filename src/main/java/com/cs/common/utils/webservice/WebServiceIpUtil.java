package com.cs.common.utils.webservice;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.lang3.StringUtils;

import com.cs.common.utils.IpUtil;

public class WebServiceIpUtil {
	/**
	 * 获得客户端IP
	 * @return
	 */
	public static String getRequestIp(){
		String ip = StringUtils.EMPTY;
	    MessageContext  mc  =  MessageContext.getCurrentContext();
	    HttpServletRequest  request  =  (HttpServletRequest)  mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
	    if(request !=null){
	    	ip = IpUtil.getClientIp(request);
	    }
		return ip;
	}
}
