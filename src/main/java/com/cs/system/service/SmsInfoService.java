package com.cs.system.service;

import java.util.Map;


public interface SmsInfoService {

	public void saveSmsCode(String mobile, String code, Integer time);

	public String getSmsCode(String mobile);

	public void deleteSmsCode(String mobile);

	public  Map<String, Object> sendMessage(String mobile,String ip);

	/**
	 * @Description: 验证短信验证码
	 * @param mobile
	 *            手机号码
	 * @param code
	 *            用户输入的短信验证码
	 * @throws Exception
	 *             Map<String,Object>
	 * @date 2016-3-2 下午1:51:36
	 */
	public Map<String, Object> checkSmsCode(String mobile, String code)
			throws Exception;
	
	/**
	 * @Description: 验证短信验证码(与普通验分开，取消获取验证码没有验黑名单，普通验证与取消发送短信验证分开)
	 * @param mobile
	 *            手机号码
	 * @param code
	 *            用户输入的短信验证码
	 * @throws Exception
	 *             Map<String,Object>
	 * @date 2017-1-20 下午1:51:36
	 */
	public Map<String, Object> checkSmsCodeNoBlack(String mobile, String code) throws Exception;
	
}
