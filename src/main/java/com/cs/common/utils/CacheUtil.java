package com.cs.common.utils;

import javax.servlet.http.HttpServletRequest;
/**  
 * 获取缓存通用类 
 * @author vincent
 *
 */
public class CacheUtil {

	/**
	 * 获取缓存对象 
	 * @param sessionName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Object getCacheObject(String cacheName , HttpServletRequest request) throws Exception{
		//TODO  扩展为通过其他方式获得缓存 
		Object object = request.getSession().getAttribute(cacheName);
		return object ;
	}
	
	/**
	 * 写入缓存对象 
	 * @param sessionName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static void setCacheObject(String cacheName , Object obj , HttpServletRequest request) throws Exception{
		//TODO  扩展为通过其他方式写入缓存 
		request.getSession().setAttribute(cacheName , obj);
	}
	
	
	public static void removeCacheObject(String cacheName , HttpServletRequest request) throws Exception{
		//TODO  扩展为通过其他方式写入缓存 
		request.getSession().removeAttribute(cacheName);
		
	}
}
