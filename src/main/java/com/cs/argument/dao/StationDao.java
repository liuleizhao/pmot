package com.cs.argument.dao;

import java.util.List;

import com.cs.argument.entity.Station;
import com.cs.mvc.dao.BaseDao;

public interface StationDao extends BaseDao<Station, String>{
	
	public List<Station> findNotExistInterfaceControl();
	
	int batchUpdateMaxNumber(List<Station> stations);
}