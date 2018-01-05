package com.cs.argument.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.argument.dao.IdTypeDao;
import com.cs.argument.entity.IdType;
import com.cs.argument.service.IdTypeService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class IdTypeServiceImpl  extends BaseServiceSupport<IdType, String>  implements IdTypeService{

	@Autowired
	private IdTypeDao idTypeDao;
	@Override
	protected BaseDao<IdType, String> getBaseDao() throws Exception {
		return idTypeDao;
	}
	@Override
	public IdType findByCode(String code) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("CODE = ", code);
		return this.findUniqueByCondition(sqlCondition);
	}

}
