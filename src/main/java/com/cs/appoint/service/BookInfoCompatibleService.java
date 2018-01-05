package com.cs.appoint.service;

import java.util.Date;

import com.cs.appoint.entity.BookInfoCompatible;
import com.cs.mvc.service.BaseService;

/**
 * 预约兼容配置Service
 * @ClassName 	BookInfoCompatibleService
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-14	 上午10:51:45
 */
public interface BookInfoCompatibleService extends BaseService<BookInfoCompatible, String> {
	/**
	 * 根据检测站ID查询
	 * @param stationId
	 * @return
	 * @throws Exception 
	 */
	public BookInfoCompatible findByStationId(String stationId) throws Exception;
	
	/**
	 * 根据检测站ID和时间查询
	 * @param stationId
	 * @param date
	 * @return
	 */
	public BookInfoCompatible findByStationIdAndDate(String stationId,Date date);
}
