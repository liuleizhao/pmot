package com.cs.appoint.service;

import java.util.Date;
import java.util.List;

import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfoSupervise;
import com.github.pagehelper.PageInfo;

/**
 * 预约信息事后监管Service
 * @ClassName:    BookInfoSuperviseService
 * @Description:  
 * @Author        succ
 * @date          2017-11-4  下午5:48:15
 */
public interface BookInfoSuperviseService {
	
	/**
	 * 查询异常记录
	 * @param bookInfoSupervise
	 * @return
	 * @Author        succ
	 * @date 2017-11-17 上午11:45:25
	 */
	public List<BookInfoSupervise> findBookInfoException(BookInfoSupervise bookInfoSupervise);
	
	/**
	 * 查询正常的业务记录
	 * @param bookInfoSupervise
	 * @return
	 */
	public List<BookInfoSupervise> findBookInfoCorrect(BookInfoSupervise bookInfoSupervise);
	
	/**
	 * 分组查询流水数量
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 * @Author        succ
	 * @date 2017-11-16 下午3:19:42
	 */
	public List<BookInfoAmount> findFlowAmountGroupByStation(Date startDate,Date endDate);
	
	/**
	 * 分组查询异常数量
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 * @Author        succ
	 * @date 2017-11-16 下午3:29:55
	 */
	public List<BookInfoAmount> findExceptionAmountGroupByStation(Date startDate,Date endDate);
	
	/**
	 * 查询所有年检业务记录
	 * @param bookInfoSupervise
	 * @return
	 * @Author        succ
	 * @date 2017-11-20 下午2:56:39
	 */
	public List<BookInfoSupervise> findAllBookInfo(BookInfoSupervise bookInfoSupervise);
	
	/**
	 * 分页查询所有年检业务记录
	 * @param bookInfoSupervise
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @Author        succ
	 * @date 2017-11-20 下午3:38:30
	 */
	public PageInfo<BookInfoSupervise> findPagedBookInfo(BookInfoSupervise bookInfoSupervise, Integer pageNum, Integer pageSize);
}
