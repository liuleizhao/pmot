package com.cs.appoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.CompApplyFromDao;
import com.cs.appoint.entity.CompApplyFrom;
import com.cs.appoint.service.CompApplyFromService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class CompApplyFromServiceImpl  extends BaseServiceSupport<CompApplyFrom, String> implements CompApplyFromService {

	@Autowired
	private CompApplyFromDao compApplyFromdao;
	
	@Override
	protected BaseDao<CompApplyFrom, String> getBaseDao() throws Exception {
		
		return compApplyFromdao;
	}
	

}
