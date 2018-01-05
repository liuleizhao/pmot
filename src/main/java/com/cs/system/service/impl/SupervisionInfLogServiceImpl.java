package com.cs.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.system.dao.SupervisionInfLogDao;
import com.cs.system.entity.SupervisionInfLog;
import com.cs.system.service.SupervisionInfLogService;




@Service
@Transactional
public class SupervisionInfLogServiceImpl extends
		BaseServiceSupport<SupervisionInfLog, String> implements SupervisionInfLogService {

	@Autowired
	private SupervisionInfLogDao supervisionInfLogDao;

	@Override
	protected BaseDao<SupervisionInfLog, String> getBaseDao() throws Exception {
		return supervisionInfLogDao;
	}

	
}
