package com.cs.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.common.constant.CacheConstant;
import com.cs.common.utils.RedisUtil;
import com.cs.common.utils.sms.SMSWebUtil;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.system.dao.SmsInfoDao;
import com.cs.system.entity.SmsInfo;
import com.cs.system.service.SmsInfoService;

@Service
@Transactional
public class SmsInfoServiceImpl extends BaseServiceSupport<SmsInfo, String>  implements SmsInfoService{
	@Autowired
	private SmsInfoDao SmsInfoDao;
	
	@Override
	protected BaseDao<SmsInfo, String> getBaseDao() throws Exception {
		return SmsInfoDao;
	}
	
	@Override
	public void saveSmsCode(String mobile, String code, Integer time) {
		RedisUtil.setValue(mobile, code, time);
	}

	@Override
	public String getSmsCode(String mobile) {
		return RedisUtil.getValue(mobile);
	}

	@Override
	public void deleteSmsCode(String mobile) {
		RedisUtil.delByKey(mobile);

	}


	@Override
	public Map<String, Object> sendMessage(String mobile,String ip) {
		String message = "";// 返回前台页面的提示信息
		int codes = 0; // 返回前台页面的提示信息对应的代码
		String status = "fail";
		Map<String, Object> result = new HashMap<String, Object>();
		// 生成短信验证码
				String number = String
						.valueOf((int) ((Math.random() * 9 + 1) * 100000));
				// 发送短信验证码
				//String smsContent = "尊敬的用户您本次网上预约的短信验证码为：" + number+ ",如非本人操作请忽略!";
				//Map<String, String> resultCode = SMSWebUtil.callWSForSMS(mobile,content);
				// 本地测试
		 		Map<String, String> resultCode = new HashMap<String ,String>();
		 		resultCode.put("code", "1"); 
				
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setMobile(mobile);
				smsInfo.setSmsCode(number);
				smsInfo.setCreateDate(new Date());
				smsInfo.setIp(ip);
				if ("1".equals(resultCode.get("code"))) {
					// 存10分钟有效短信验证码
					//saveSmsCode(mobile, mobile + "_" + number, 60 * 10);
					message = "短信验证码已发送至手机：[" + mobile + "],请注意查收.";
					codes = 0;
					status = "success";
					smsInfo.setStatus(0);
					SmsInfoDao.insert(smsInfo);
					saveSmsCode(CacheConstant.MOT_SEND_MSG_PREFIX+mobile,mobile + "_" + number,
							CacheConstant.DYNAMIC_CODE_TIME_OUT);
				} else {
					message = "短信验证码发送失败，原因:[" + resultCode.get("msg") + "]";
					codes = 1;
					smsInfo.setStatus(1);
					smsInfo.setResult(message);
					SmsInfoDao.insert(smsInfo);
				}
				result.put("code", codes);
				result.put("msg", message);
				result.put("status", status);
				System.out.println(number);
				return result;
	}
	
	
	@Override
	public Map<String, Object> checkSmsCode(String mobile, String code)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		/**
		 * codes :获取验证码或者校验验证码的返回结果， 0:发送成功， 1:发送失败， 2：30(企业预约10)分钟内超过次数限制，
		 * 3：已发送（1分钟内重复发送）， 4：失效（验证短信验证码时,验证码过期）， 5:通过验证(短信验证码正确)
		 * 6:验证失败（短信验证码错误，手机号码不符） 7:已进入短信黑名单 8:一天一笔限制 9：一月三笔限制 10:代办员黑名单
		 * 11:代办员无电子委托 12:企业代办发送短信成功(快速将预约类型传回预约页面)
		 * 
		 */
		String message = "";
		int codes = 5;
		String status = "fail";
		String smsCode = getSmsCode(CacheConstant.MOT_SEND_MSG_PREFIX+mobile);
		boolean flag = true;
		if (flag && StringUtils.isBlank(smsCode)) {
			codes = 4;
			message = "短信验证码未发送或已过期,请重新获取";
			flag = false;
		} else {
			if (flag
					&& !smsCode.substring(0, smsCode.indexOf("_")).equals(
							mobile)) {
				codes = 6;
				message = "手机号码与接收短信验证码手机号不符,请确认";
				flag = false;
			}
			
			if (flag
					&& !code.equals(smsCode.substring(smsCode.indexOf("_") + 1))) {
				message = "短信验证码错误,请重新输入";
				codes = 6;
				flag = false;
			} else if(flag){
				// 验证通过
				codes = 5;
				status = "success";
				//清缓存
				deleteSmsCode(CacheConstant.MOT_SEND_MSG_PREFIX+mobile);
			}
		}
		result.put("code", codes);
		result.put("msg", message);
		result.put("status", status);
		return result;
	}

	@Override
	public Map<String, Object> checkSmsCodeNoBlack(String mobile, String code)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
