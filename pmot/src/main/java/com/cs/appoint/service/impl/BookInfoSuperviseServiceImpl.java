package com.cs.appoint.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.VehIsFlowDao;
import com.cs.appoint.dto.BookInfoAmount;
import com.cs.appoint.entity.BookInfoSupervise;
import com.cs.appoint.service.BookInfoSuperviseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class BookInfoSuperviseServiceImpl implements BookInfoSuperviseService {

	@Autowired
	private VehIsFlowDao vehIsFlowDao;
	
	@Override
	public List<BookInfoSupervise> findBookInfoCorrect(
			BookInfoSupervise bookInfoSupervise) {
		return vehIsFlowDao.findBookInfoCorrect(bookInfoSupervise);
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
		List<BookInfoSupervise> list = null;
		if(bookInfoSupervise!=null){
			Integer isException = bookInfoSupervise.getIsException();
			if(isException==null){
				list = this.findAllBookInfo(bookInfoSupervise);
			}else{
				if(isException==1){
					list = this.findBookInfoException(bookInfoSupervise);
				}
				if(isException==0){
					list = this.findBookInfoCorrect(bookInfoSupervise);
				}
			}
		}
		PageInfo<BookInfoSupervise> pageView = new PageInfo<BookInfoSupervise>(list);
		return pageView;
	}
}
