package com.cs.webservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.common.utils.DateUtils;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.webservice.dao.InterfaceInvokeCounterDao;
import com.cs.webservice.entity.InterfaceInvokeCounter;
import com.cs.webservice.service.InterfaceInvokeCounterService;

@Service
@Transactional
public class InterfaceInvokeCounterServiceImpl extends BaseServiceSupport<InterfaceInvokeCounter, String> 
	implements InterfaceInvokeCounterService {

	private static String INVOKE_DATE = null;
	
	@Autowired
	private InterfaceInvokeCounterDao interfaceInvokeCounterDao;
	
	@Override
	protected BaseDao<InterfaceInvokeCounter, String> getBaseDao()
			throws Exception {
		return interfaceInvokeCounterDao;
	}

	@Override
	public InterfaceInvokeCounter findByInvokeDate(String invokeDate) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("INVOKE_DATE = ", invokeDate);
		return this.findUniqueByCondition(sqlCondition);
	}

	@Override
	public void plusOne() throws Exception {
		String invokeDate = DateUtils.getDate();
		int result = interfaceInvokeCounterDao.plusOneByInvokeDate(invokeDate);
		if(result == 0){
			createNewInvokeCounter();
		}
	}
	
	private void createNewInvokeCounter() throws Exception{
		String invokeDate = DateUtils.getDate();
		InterfaceInvokeCounter newCounter = new InterfaceInvokeCounter();
		newCounter.setInvokeDate(invokeDate);
		newCounter.setInvokeCount(1);
		this.insert(newCounter);
		INVOKE_DATE = invokeDate;
	}

}
