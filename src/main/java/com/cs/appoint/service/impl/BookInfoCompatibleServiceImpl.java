package com.cs.appoint.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.BookInfoCompatibleDao;
import com.cs.appoint.entity.BookInfoCompatible;
import com.cs.appoint.service.BookInfoCompatibleService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class BookInfoCompatibleServiceImpl extends
		BaseServiceSupport<BookInfoCompatible, String> implements
		BookInfoCompatibleService {

	@Autowired
	private BookInfoCompatibleDao bookInfoCompatibleDao;
	
	@Override
	protected BaseDao<BookInfoCompatible, String> getBaseDao() throws Exception {
		return bookInfoCompatibleDao;
	}

	@Override
	public BookInfoCompatible findByStationId(String stationId) throws Exception {
		BookInfoCompatible bookInfoCompatible = null;
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("STATION_ID = ", stationId);
		bookInfoCompatible = this.findUniqueByCondition(sqlCondition);
		return bookInfoCompatible;
	}

	@Override
	public BookInfoCompatible findByStationIdAndDate(String stationId, Date date) {
		return bookInfoCompatibleDao.findByStationIdAndDate(stationId, date);
	}

	
}
