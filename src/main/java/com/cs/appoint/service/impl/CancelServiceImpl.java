package com.cs.appoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.CancelDao;
import com.cs.appoint.entity.Cancel;
import com.cs.appoint.service.CancelService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class CancelServiceImpl extends BaseServiceSupport<Cancel, String> implements CancelService {
	
	@Autowired
	private CancelDao cancelDao;
	
	@Override
	protected BaseDao<Cancel, String> getBaseDao() throws Exception {
		return cancelDao;
	}
	
}
