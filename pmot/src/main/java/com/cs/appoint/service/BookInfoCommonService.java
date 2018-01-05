package com.cs.appoint.service;

import com.cs.appoint.entity.BookInfo;
import com.cs.argument.entity.Station;

public interface BookInfoCommonService {
	
	/**
	 * 预约统一验证
	 * 1.验证检测站是否能预约该车辆性质的车
	 * 2.检查时间是否已过
	 * 3.判断最大预约量
	 * 4.判断是否该车是否已经预约
	 * */
	public String unifiedValidate(BookInfo bookInfo,Station station,String type); 
	
}

