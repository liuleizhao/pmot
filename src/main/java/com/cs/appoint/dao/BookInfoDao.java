package com.cs.appoint.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfo;
import com.cs.mvc.dao.BaseDao;

public interface BookInfoDao extends BaseDao<BookInfo, String>{

	public void updateByBookNumber(int bookState, String bookNumber);
	/**
	 * 分组查询各检测站的预约完成数量
	 * @return
	 * @Author        succ
	 * @date 2017-11-15 下午6:13:17
	 */
	public List<BookInfoAmount> findFinishedBookAmountGroupByStation(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	/**
	 * 查询指定预约日期下但没有下载车辆信息的预约记录
	 * @param bookDate
	 * @return
	 */
	public List<BookInfo> findByBookDateWithoutVehInfo(String bookDate);
	
	
    List<BookInfo> queryCurrentAppoint(Map<String, Object> map);
    
    
    /**
     * 查询流水状态没有完结的预约记录
     * @return
     */
    public List<BookInfo> findCheckStateNotFinished();
    
    public void batchAdd(List<BookInfo> bookInfos);
    
    /**
	 * 查询指定预约日期下但没有下载车辆信息的预约记录
	 * @param bookDate
	 * @return
	 */
    public List<BookInfo> findByBookDateListWithoutVehInfo(List<String> bookDate);
}