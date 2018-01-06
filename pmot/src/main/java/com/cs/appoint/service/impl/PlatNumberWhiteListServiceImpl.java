package com.cs.appoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.dao.PlatNumberWhiteListDao;
import com.cs.appoint.entity.PlatNumberWhiteList;
import com.cs.appoint.service.PlatNumberWhiteListService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class PlatNumberWhiteListServiceImpl extends BaseServiceSupport<PlatNumberWhiteList, String> 
	implements PlatNumberWhiteListService{

	@Autowired
	private PlatNumberWhiteListDao platNumberWhiteListDao;
	
	@Override
	protected BaseDao<PlatNumberWhiteList, String> getBaseDao()
			throws Exception {
		return platNumberWhiteListDao;
	}

}
