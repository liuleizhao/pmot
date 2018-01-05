package com.cs.appoint.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.CompleteDao;
import com.cs.appoint.entity.Complete;
import com.cs.appoint.service.CompleteService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class CompleteServiceImpl extends BaseServiceSupport<Complete, String> implements CompleteService {
	
	@Autowired
	private CompleteDao completeDao;
	
	@Override
	protected BaseDao<Complete, String> getBaseDao() throws Exception {
		
		return completeDao;
	}

	@Override
	public List<Complete> findComplete(String stationId, Date startDate, Date endDate) throws Exception {
		return completeDao.findComplete(stationId,startDate,endDate);
	}
	
}
