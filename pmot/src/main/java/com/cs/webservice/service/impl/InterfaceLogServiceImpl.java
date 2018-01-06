package com.cs.webservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
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
		return logDao.findLatestByIp(ip);
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
