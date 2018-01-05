package com.cs.argument.dao;

import java.util.List;

import com.cs.argument.entity.CarType;
import com.cs.mvc.dao.BaseDao;

public interface CarTypeDao  extends BaseDao<CarType, String>{
	
	public List<CarType> findAllDataByOrder(String orderbyClause);
}