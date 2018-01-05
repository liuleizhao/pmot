package com.cs.system.dao;

import com.cs.mvc.dao.BaseDao;
import com.cs.system.entity.FreeVerificationBookConfig;

public interface FreeVerificationBookConfigDao extends BaseDao<FreeVerificationBookConfig,String> {
	public FreeVerificationBookConfig findValidConfigById(String stationId);
}