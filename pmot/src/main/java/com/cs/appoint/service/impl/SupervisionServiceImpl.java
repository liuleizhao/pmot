package com.cs.appoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.service.SupervisionService;
import com.cs.common.utils.RequestUtils;
import com.cs.system.dao.SupervisionInfLogDao;


@Service
@Transactional
public class SupervisionServiceImpl implements SupervisionService{

	@Autowired
	private SupervisionInfLogDao supervisionInfLogDao;


	@Override
	public String queryObjectOut(String xtlb, String jkxlh, String jkid,
				String QueryXmlDoc) throws Exception {
			String responseXml=RequestUtils.queryObjectOut(xtlb, jkxlh, jkid, QueryXmlDoc);
			return responseXml;
		
	}
	
	
}
