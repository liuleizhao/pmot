package com.cs.appoint.webservice;

import java.util.Date;

import com.cs.appoint.entity.BookInfo;


/**
 * 预约信息WebService接口
 * @ClassName:    BookInfoWebService
 * @Description:  
 * @Author        succ
 * @date          2017-11-1  下午1:24:12
 */
public interface BookInfoWebService {
	
	/**
	 * 检验是否预约，并获取车辆信息接口
	 * @param bookNumber	预约号
	 * @param verifyCode	验证码
	 * @return
	 */
	public String getVehicleInfo(String bookNumber,String verifyCode);
	
	public boolean validBookTime(BookInfo bookInfo,Date nowDate) throws Exception;
	
	public String preGetVehicleInfo(String platNumber,String carType,String frameNumber,String verifyCode);
}
