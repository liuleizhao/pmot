package com.cs.appoint.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfoSupervise;
import com.cs.appoint.entity.VehIsFlow;
import com.cs.mvc.dao.BaseDao;

/**
 * 检验信息流水表DAO
 * @ClassName:    VehIsFlowDao
 * @Description:  
 * @Author        succ
 * @date          2017-11-6  上午10:49:43
 */
public interface VehIsFlowDao extends BaseDao<VehIsFlow,String> {
	
	/**
	 * 查询异常记录
	 * @param bookInfoSupervise
	 * @return
	 * @Author        succ
	 * @date 2017-11-17 上午11:45:25
	 */
	public List<BookInfoSupervise> findBookInfoException(BookInfoSupervise bookInfoSupervise);
	
	/**
	 * 查询所有记录
	 * @param bookInfoSupervise
	 * @return
	 * @Author        succ
	 * @date 2017-11-20 下午2:56:39
	 */
	public List<BookInfoSupervise> findAllBookInfo(BookInfoSupervise bookInfoSupervise);

	/**
	 * 分组查询实际办理数量
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 * @Author        succ
	 * @date 2017-11-16 下午3:19:42
	 */
	public List<BookInfoAmount> findFlowAmountGroupByStation(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	/**
	 * 分组查询异常数量
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 * @Author        succ
	 * @date 2017-11-16 下午3:29:55
	 */
	public List<BookInfoAmount> findExceptionAmountGroupByStation(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}