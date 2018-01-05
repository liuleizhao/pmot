package com.cs.appoint.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.VehIsFlowDao;
import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfoSupervise;
import com.cs.appoint.entity.VehIsFlow;
import com.cs.appoint.service.VehIsFlowService;
import com.cs.common.entityenum.BookState;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class VehIsFlowServiceImpl extends BaseServiceSupport<VehIsFlow, String> implements VehIsFlowService {

	@Autowired
	private VehIsFlowDao vehIsFlowDao;
	
	@Override
	protected BaseDao<VehIsFlow, String> getBaseDao() throws Exception {
		return vehIsFlowDao;
	}
	
	@Override
	public List<BookInfoAmount> findFlowAmountGroupByStation(Date startDate,
			Date endDate) {
		return vehIsFlowDao.findFlowAmountGroupByStation(startDate, endDate);
	}

	@Override
	public List<BookInfoAmount> findExceptionAmountGroupByStation(
			Date startDate, Date endDate) {
		return vehIsFlowDao.findExceptionAmountGroupByStation(startDate, endDate);
	}

	@Override
	public List<BookInfoSupervise> findBookInfoException(
			BookInfoSupervise bookInfoSupervise) {
		return vehIsFlowDao.findBookInfoException(bookInfoSupervise);
	}

	@Override
	public List<BookInfoSupervise> findAllBookInfo(
			BookInfoSupervise bookInfoSupervise) {
		return vehIsFlowDao.findAllBookInfo(bookInfoSupervise);
	}

	@Override
	public PageInfo<BookInfoSupervise> findPagedBookInfo(
			BookInfoSupervise bookInfoSupervise, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BookInfoSupervise> list = this.findAllBookInfo(bookInfoSupervise);
		//更新记录的异常状态
		updateExceptionStatus(list);
		PageInfo<BookInfoSupervise> pageView = new PageInfo<BookInfoSupervise>(list);
		return pageView;
	}

	/**
	 * 更新异常状态
	 * @param bookInfoSupervise
	 * @Author        succ
	 * @date 2017-11-20 上午9:42:07
	 */
	private void updateExceptionStatus(List<BookInfoSupervise> list){
		if(CollectionUtils.isNotEmpty(list)){
			for(BookInfoSupervise bookInfoSupervise : list){
				String bookNumber = bookInfoSupervise.getBookNumber();
				String lsh = bookInfoSupervise.getLsh();
				BookState bookState = bookInfoSupervise.getBookState();
				if(StringUtils.isBlank(bookNumber) && StringUtils.isNotBlank(lsh)){
					//BookInfo表没有记录，VehIsFlow有记录
					bookInfoSupervise.setIsException(1);
				}else if(BookState.YYWC.equals(bookState) && StringUtils.isBlank(lsh)){
					//VehIsFlow没有记录，BookInfo表显示预约完成的
					bookInfoSupervise.setIsException(1);
				}else if(!BookState.YYWC.equals(bookState) && StringUtils.isNotBlank(lsh)){
					//预约状态显示未完成，但已办理
					bookInfoSupervise.setIsException(1);
				}else{
					bookInfoSupervise.setIsException(0);
				}
			}
		}
	}
}
