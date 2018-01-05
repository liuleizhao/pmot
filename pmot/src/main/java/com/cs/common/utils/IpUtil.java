package com.cs.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;

public class IpUtil {

	/**
	 * 获取用户ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}

		return ip;

	}

	/**
	 * AXIS获取客户端HttpServletRequest
	 * 
	 * @param request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
        try {
            MessageContext mc = MessageContext.getCurrentContext();
            HttpServletRequest request = null;
            if(null != mc){
            	request = (HttpServletRequest) mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
            } 
            return request;
        } catch (Exception e) {
            System.out.println("无法获取对方主机IP");
            e.printStackTrace();
            return null;
        }
    }
	
}
