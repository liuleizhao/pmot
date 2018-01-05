package com.cs.argument.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.argument.dao.OrgDao;
import com.cs.argument.entity.Org;
import com.cs.argument.service.OrgService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class OrgServiceImpl extends BaseServiceSupport<Org, String>  implements OrgService{
	@Autowired
	private OrgDao orgDao;
	
	@Override
	protected BaseDao<Org, String> getBaseDao() throws Exception {
		return orgDao;
	}

	@Override
	public List<Org> findOrgOpen() throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("STATUS =",1);
		List<Org> list = orgDao.findByCondition(sqlCondition);
		return list;
	}

	@Override
	public Org findByCode(String code) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("code =",code);
		List<Org>  list = orgDao.findByCondition(sqlCondition);
		if(null != list && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}

}
