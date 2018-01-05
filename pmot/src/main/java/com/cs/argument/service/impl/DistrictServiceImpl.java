package com.cs.argument.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.argument.dao.DistrictDao;
import com.cs.argument.entity.District;
import com.cs.argument.service.DistrictService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class DistrictServiceImpl extends BaseServiceSupport<District, String> implements DistrictService{

	@Autowired
	private DistrictDao districtDao;
	
	@Override
	protected BaseDao<District, String> getBaseDao() throws Exception {
		return districtDao;
	}
	
	
}
