package com.cs.appoint.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.cs.appoint.entity.BookInfoCompatible;
import com.cs.mvc.dao.BaseDao;

/**
 * 预约兼容Dao
 * @ClassName:    BookInfoCompatibleDao
 * @Description:  
 * @Author        succ
 * @date          2017-11-10  下午5:36:54
 */
public interface BookInfoCompatibleDao extends BaseDao<BookInfoCompatible,String> {
	/**
	 * 根据检测站ID和时间查询
	 * @param stationId
	 * @param date
	 * @return
	 */
	public BookInfoCompatible findByStationIdAndDate(@Param("stationId")String stationId,@Param("date")Date date);
}