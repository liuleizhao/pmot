package com.cs.webservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.webservice.dao.InterfaceParameterDao;
import com.cs.webservice.entity.InterfaceParameter;
import com.cs.webservice.service.InterfaceParameterService;

@Service
@Transactional
public class InterfaceParameterServiceImpl extends BaseServiceSupport<InterfaceParameter, String> 
		implements InterfaceParameterService{

	@Autowired
	private InterfaceParameterDao interfaceParameterDao;

	@Override
	protected BaseDao<InterfaceParameter, String> getBaseDao() throws Exception {
		return interfaceParameterDao;
	}

	@Override
	public InterfaceParameter findByNameAndType(String name, String type) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("NAME = ", name);
		sqlCondition.addSingleNotNullCriterion("TYPE = ", type);
		return this.findUniqueByCondition(sqlCondition);
	}
	
	 
}
