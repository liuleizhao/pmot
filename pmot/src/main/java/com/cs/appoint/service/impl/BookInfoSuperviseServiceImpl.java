package com.cs.appoint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.VehIsFlowDao;
import com.cs.appoint.entity.BookInfoSupervise;
import com.cs.appoint.service.BookInfoSuperviseService;

@Service
@Transactional
public class BookInfoSuperviseServiceImpl implements BookInfoSuperviseService {

	@Autowired
	private VehIsFlowDao vehIsFlowDao;
	
	@Override
	public List<BookInfoSupervise> findBookInfoException(BookInfoSupervise bookInfoSupervise) {
		return vehIsFlowDao.findBookInfoException(bookInfoSupervise);
	}

}
