package com.cs.webservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.webservice.dao.InterfaceControlGeneralDao;
import com.cs.webservice.entity.InterfaceControlGeneral;
import com.cs.webservice.service.InterfaceControlGeneralService;

@Service
@Transactional
public class InterfaceControlGeneralServiceImpl extends BaseServiceSupport<InterfaceControlGeneral,String> implements InterfaceControlGeneralService{

	
	@Autowired
	private InterfaceControlGeneralDao generalDao;
	
	@Override
	protected BaseDao<InterfaceControlGeneral, String> getBaseDao()
			throws Exception {
		return generalDao;
	}

	@Override
	public InterfaceControlGeneral findByIp(String ip) throws Exception {
		SqlCondition generalSqlCondition = new SqlCondition();
		generalSqlCondition.addLikeCriterion("ips like ", "$"+ip+"$");
		InterfaceControlGeneral general = this.findUniqueByCondition(generalSqlCondition);
		return general;
	}

	@Override
	public InterfaceControlGeneral findBySerialNumber(String serialNumber)
			throws Exception {
		SqlCondition generalSqlCondition = new SqlCondition();
		generalSqlCondition.addSingleNotNullCriterion("SERIAL_NUMBER = ", serialNumber);
		return this.findUniqueByCondition(generalSqlCondition);
	}

	@Override
	public InterfaceControlGeneral findByStationId(String stationId)
			throws Exception {
		SqlCondition generalSqlCondition = new SqlCondition();
		generalSqlCondition.addSingleNotNullCriterion("STATION_ID = ", stationId);
		return this.findUniqueByCondition(generalSqlCondition);
	}

}
