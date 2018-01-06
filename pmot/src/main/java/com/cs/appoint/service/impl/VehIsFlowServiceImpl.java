package com.cs.appoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.VehIsFlowDao;
import com.cs.appoint.entity.VehIsFlow;
import com.cs.appoint.service.VehIsFlowService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class VehIsFlowServiceImpl extends BaseServiceSupport<VehIsFlow, String> implements VehIsFlowService {

	@Autowired
	private VehIsFlowDao vehIsFlowDao;
	
	@Override
	protected BaseDao<VehIsFlow, String> getBaseDao() throws Exception {
		return vehIsFlowDao;
	}

}
