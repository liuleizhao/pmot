package com.cs.appoint.service;

import java.util.Date;
import java.util.List;

import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.CompApplyFrom;
import com.cs.appoint.vo.ComApplyBookInfoVO;
import com.cs.argument.entity.Station;
import com.cs.mvc.service.BaseService;
import com.cs.system.entity.User;

public interface BookInfoService extends BaseService<BookInfo, String>{
	
	public void updateByBookNumber(int bookState, String bookNumber);
	
	public BookInfo selectByBookNumber(String bookNumber) throws Exception;
	/**
	 * 分组查询各检测站的预约完成数量
	 * @return
	 * @Author        succ
	 * @date 2017-11-15 下午6:13:17
	 */
	public List<BookInfoAmount> findFinishedBookAmountGroupByStation(Date startDate,Date endDate);
	
	/**
	 * 绿色通道不更新预约量
	 * @param bookInfo
	 * @return
	 * @throws Exception
	 */
	public String insertBookInfo(BookInfo bookInfo,String type);
	
	
	public String returnNumber(String bookNumber,String verifyCode);
	
	/**
	 * 查询是否存在已预约信息
	 * @return
	 */
	public BookInfo checkIsExistsAppoint(BookInfo bookInfo) throws Exception;
	
	/**
	 * 查询专网当前检测站当天的预约数据
	 * @param stationId（检测站id）
	 * @param type 预约类型（绿色通道，大客户），主要传预约号码标记
	 * @return
	 */
	public List<BookInfo> queryCurrentAppoint(String stationId,String type) throws Exception;
	
	public int queryCurrentCount(String stationId,String type) throws Exception;
	
	 /**
     * 查询流水状态没有完结的预约记录
     * @return
     */
    public List<BookInfo> findCheckStateNotFinished();
    
    public String batchAdd(List<BookInfo> bookInfos);
    
    /***
     * 批量新增预约信息，返回以预约信息
     * @param bookInfo
     * @param comBooks
     * @param backendUser
     * @param compApplyFrom
     * @return
     */
    List<BookInfo> batchAddCompAplyy(BookInfo bookInfo, List<ComApplyBookInfoVO> comBooks,User backendUser,CompApplyFrom compApplyFrom) throws Exception ;
    
	public BookInfo checkBooking(String carTypeId,String platNumber) throws Exception;
}
