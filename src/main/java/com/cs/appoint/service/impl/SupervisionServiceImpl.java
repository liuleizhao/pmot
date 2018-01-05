package com.cs.appoint.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.service.SupervisionService;
import com.cs.common.utils.RequestUtils;
import com.cs.system.dao.SupervisionInfLogDao;
import com.cs.system.entity.SupervisionInfLog;


@Service
@Transactional
public class SupervisionServiceImpl implements SupervisionService{

	@Autowired
	private SupervisionInfLogDao supervisionInfLogDao;


		@Override
		public String queryObjectOut(String xtlb, String jkxlh, String jkid,
				String QueryXmlDoc) throws Exception {
			Date requestDate=new Date();
			String responseXml=RequestUtils.queryObjectOut(xtlb, jkxlh, jkid, QueryXmlDoc);
			Date reponseDate=new Date();
			SupervisionInfLog supervisionInfLog=new SupervisionInfLog();
			supervisionInfLog.setInterfaceNum(jkid);
			supervisionInfLog.setRequestXml(QueryXmlDoc);
			supervisionInfLog.setResponseXml(responseXml);
			supervisionInfLog.setResponseTime(reponseDate);
			supervisionInfLog.setRequestTime(requestDate);
			supervisionInfLog.setRunTime((new Long((new Date().getTime() - requestDate.getTime()))).intValue());
			try {
				supervisionInfLogDao.insert(supervisionInfLog);
			} catch (Exception e) {
				
			}
			
			return responseXml;
		
	}
	
	
}
