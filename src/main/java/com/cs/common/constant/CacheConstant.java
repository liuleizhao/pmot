package com.cs.common.constant;

public class CacheConstant {

	/**客户端用户Sesison  */
	public static String CLIENT_USER_CACHE = "client_user_session";
	
	public static String BACKEND_USER_CACHE = "backend_user_session";
	
	/** 保存发送年检预约短信的前缀*/
	public static String MOT_SEND_MSG_PREFIX = "mot_send_msg_prefix";
	
	/** 预约动态码保存时间（秒） */
	public final static Integer DYNAMIC_CODE_TIME_OUT = 3600;
	
	/** 核保单号缓存前缀 */
	//public static String HB_SERIALNUMBER_PERFIX = "hb_serialnumber_";
	
	/** 用户资源缓存前缀*/
	public static String USER_ACCESS_AUTHORITY_KEY = "user_access_authority_key";
 
}
