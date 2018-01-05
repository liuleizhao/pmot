package com.cs.common.constant;

public class Constants {
	
	/** 系统管理员名称 */
	public static final String SUPER_SYSTEM_ADMIN = "系统管理员";

	/** 保存在session中登录用户的键值 */
	public final static String SESSION_USER = "session_user";
	
	/** 预约动态码保存时间（秒） */
	public final static Integer DYNAMIC_CODE_TIME_OUT = 1800;
	
	/** 预约动态码保存时间（秒） */
	public final static Integer QUERY_CODE_TIME_OUT = 3600;
	
	/** 用户访问资源KEY前缀_ */
	public final static String USER_ACCESS_AUTHORITY_KEY = "user_access_authority_key_"; 
	
	/** 用户访问资源更新时间 */
	public final static String USER_ACCESS_AUTHORITY_UPDATE_TIME = "user_access_authority_update_time_";
	
	/** 用户访问资源KEY前缀_ */
	public final static String SYSTEM_ACCESS_AUTHORITY_UPDATE_TIME = "system_access_authority_update_time_"; 
	
	/** 错误车辆信息记录 */
	public final static String ERROR_VEHINFO_RECORD = "error_vehinfo_record_";
	
	/** 下载车辆信息失败次数 */
	public final static String DOWNLOAD_VEHINFO_FAIL_COUNT = "download_vehinfo_fail_count_";
	
	/** 存储号牌种类，编码前缀_ */
	public final static String CAR_TYPE_CODE_PREFIX = "CAR_TYPE_CODE_PREFIX_";
	
	/** 存储号牌种类，ID前缀_ */
	public final static String CAR_TYPE_ID_PREFIX = "CAR_TYPE_ID_PREFIX__";
	
	/** 下载机动车信息接口ID  */
	public final static String GET_VEHICLE_INFO_JKID = "18C49";
	/** 下载安检机构工作人员的基本信息ID  */
	public final static String GET_VEH_INSPECTION_WORKER = "18C05";
	
	
	/** 时间同步 */
	public final static String SYNCHRONIZ_TIME_JKID = "18C50";
	
	/** 登录接口ID */
	public final static String LONGIN_JKID = "18C51";
	
	/** 接口类别 */
	public final static String XTLB = "18";
	
	/** 不存在  */
	
	/** 错误的  */
	public final static String ERROR_CODE = "-1"; 
	
	public final static String NULL_CODE = "0"; 
	
	/** 不存在  */
	public final static String SUCCESS_CODE = "1"; 
	
	public final static String WAIT_CODE = "99"; 
	
	
	/** 审验中心接口序列号  */
//	public final static String  JKXLH= "781A0909020517040015FBDFEBE8F3E2FD9EF39689C2E1F7F3C1D19EB8C7B1DBCEF1CFB5CDB3";
	public final static String  JKXLH= "781A0909020517040015FBDFEBE8F3E28D8D879BE49FE18BFB8EDBDDDA9BADDCC6F8C5C5B7C5BCECB2E2D0C5CFA2C9A8C3E8B2C9BCAFCFB5CDB3";

	/** 同步预约信息接口地址 */
	public final static String SYN_BOOK_INFO_URL="http://localhost:8891/sync_system/services/syncService?wsdl";
	public final static String SYN_BOOK_INFO_QUERY="queryObject";
	public final static String SYN_BOOK_INFO_WRITE="writeObject";
}
