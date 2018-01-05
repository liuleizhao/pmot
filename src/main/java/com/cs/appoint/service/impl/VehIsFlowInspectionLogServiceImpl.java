package com.cs.appoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.VehIsFlowInspectionLogDao;
import com.cs.appoint.entity.VehIsFlowInspectionLog;
import com.cs.appoint.service.VehIsFlowInspectionLogService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;


@Service
@Transactional
public class VehIsFlowInspectionLogServiceImpl extends BaseServiceSupport<VehIsFlowInspectionLog, String> 
	implements VehIsFlowInspectionLogService {

	@Autowired
	private VehIsFlowInspectionLogDao vehIsFlowInspectionLogDao;
	
	@Override
	protected BaseDao<VehIsFlowInspectionLog, String> getBaseDao()
			throws Exception {
		return vehIsFlowInspectionLogDao;
	}

}
