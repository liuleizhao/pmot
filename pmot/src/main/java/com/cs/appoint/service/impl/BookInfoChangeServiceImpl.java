package com.cs.appoint.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.BookInfoChangeDao;
import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.BookInfoChange;
import com.cs.appoint.service.BookInfoChangeService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class BookInfoChangeServiceImpl extends BaseServiceSupport<BookInfoChange, String> implements BookInfoChangeService{

	@Autowired
	private BookInfoChangeDao bookInfoChangeDao ;
	
	@Override
	protected BaseDao<BookInfoChange, String> getBaseDao() throws Exception {
		return bookInfoChangeDao;
	}

	@Override
	public Integer insert(BookInfo bookInfo) {
		BookInfoChange bookInfoChange = new BookInfoChange();
		bookInfoChange.setBookNumber(bookInfo.getBookNumber());
		bookInfoChange.setBookState(bookInfo.getBookState());
		
		bookInfoChange.setCheckOperationMark(bookInfo.getCheckOperationMark());
		bookInfoChange.setCheckSerialNumber(bookInfo.getCheckSerialNumber());
		bookInfoChange.setCheckState(bookInfo.getCheckState());
		bookInfoChange.setCheckStation(bookInfo.getCheckStation());
		bookInfoChange.setCheckTime(bookInfo.getCheckTime());
		
		bookInfoChange.setCreateDate(new Date());
		bookInfoChange.setFrameNumber(bookInfo.getFrameNumber());
		Integer num = bookInfoChangeDao.insert(bookInfoChange);
		return num;
	}

}
