package com.cs.webservice.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.webservice.dao.InterfaceLogDao;
import com.cs.webservice.entity.InterfaceLog;
import com.cs.webservice.service.InterfaceLogService;


@Service
@Transactional
public class InterfaceLogServiceImpl extends BaseServiceSupport<InterfaceLog,String> implements InterfaceLogService{

	@Autowired
	private InterfaceLogDao logDao;
	
	@Override
	protected BaseDao<InterfaceLog, String> getBaseDao()
			throws Exception {
		return logDao;
	}

	@Override
	public InterfaceLog findLatestByIp(String ip) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("IP = ", ip);
		sqlCondition.addDescOrderbyColumn("REQUEST_DATE");
		List<InterfaceLog> list = this.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	public static void main(String[] args) {
		int i = 10;
		try{
			i = Integer.parseInt("222");
		}catch(NumberFormatException e){
		}
		System.out.println(i);
	}
}
