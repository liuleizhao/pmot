package com.cs.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.webservice.dao.InterfaceControlDetailDao;
import com.cs.webservice.entity.InterfaceControlDetail;
import com.cs.webservice.service.InterfaceControlDetailService;


@Service
@Transactional
public class InterfaceControlDetailServiceImpl extends BaseServiceSupport<InterfaceControlDetail,String> implements InterfaceControlDetailService{

	@Autowired
	private InterfaceControlDetailDao detailDao;
	
	@Override
	protected BaseDao<InterfaceControlDetail, String> getBaseDao()
			throws Exception {
		return detailDao;
	}

	@Override
	public List<InterfaceControlDetail> findByGeneralId(String generalId) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("GENERAL_ID = ", generalId);
		return this.findByCondition(sqlCondition);
	}

	@Override
	public int deleteByGeneralId(String generalId) throws Exception {
		return detailDao.deleteByGeneralId(generalId);
	}

	@Override
	public int batchAdd(List<InterfaceControlDetail> list) throws Exception {
		return detailDao.batchAdd(list);
	}

}
