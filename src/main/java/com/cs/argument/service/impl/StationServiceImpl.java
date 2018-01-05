package com.cs.argument.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.argument.dao.StationDao;
import com.cs.argument.entity.Station;
import com.cs.argument.service.StationService;
import com.cs.common.entityenum.StationStatus;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class StationServiceImpl extends
		BaseServiceSupport<Station, String> implements StationService {

	@Autowired
	private StationDao stationInfoDao;

	@Override
	protected BaseDao<Station, String> getBaseDao() throws Exception {
		return stationInfoDao;
	}

	@Override
	public Station findByCodeOrName(Integer type, String codeOrName,
			String stationId) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if (null != type && type == 1) {
			sqlCondition.addSingleNotNullCriterion("code =", codeOrName);
		}
		if (null != type && type == 2) {
			sqlCondition.addSingleNotNullCriterion("name=", codeOrName);
		}
		if (StringUtils.isNotBlank(stationId)) {
			sqlCondition.addCriterion(" id != '" + stationId + "'");
		}
		List<Station> users = stationInfoDao.findByCondition(sqlCondition);
		if (null != users && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public List<Station> findStationsByDistrict(String districtId) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		if (StringUtils.isNotBlank(districtId)) {
			sqlCondition.addSingleCriterion("district_Id =", districtId);
			sqlCondition.addSingleNotNullCriterion("STATION_STATE =", StationStatus.YES);
		}
		List<Station> stations = stationInfoDao.findByCondition(sqlCondition);

		return stations;
	}

	@Override
	public List<Station> findStationOpen() throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("STATION_STATE =", StationStatus.YES);
		List<Station> stationInfos = stationInfoDao.findByCondition(sqlCondition);
		return stationInfos;
	}

	@Override
	public Station findByCode(String code) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("code =", code);
		List<Station> list = this.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Station findByName(String name) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("NAME =", name);
		return this.findUniqueByCondition(sqlCondition);
	}

	@Override
	public List<Station> findNotExistInterfaceControl() throws Exception {
		return stationInfoDao.findNotExistInterfaceControl();
	}

	@Override
	public int batchUpdateMaxNumber(List<Station> stations) {
		int updateCount = 0;
		if(null != stations && stations.size() > 0){
			updateCount = stationInfoDao.batchUpdateMaxNumber(stations);
		}
		return updateCount;
	}

}
