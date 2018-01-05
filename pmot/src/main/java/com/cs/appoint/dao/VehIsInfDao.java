package com.cs.appoint.dao;

import java.util.List;

import com.cs.appoint.entity.VehIsInfo;
import com.cs.mvc.dao.BaseDao;



public interface VehIsInfDao extends BaseDao<VehIsInfo, String>{
	public List<VehIsInfo> selectBySelective(VehIsInfo vehIsInfo);
}