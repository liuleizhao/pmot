package com.cs.argument.service;

import java.util.List;

import com.cs.argument.entity.CarType;
import com.cs.mvc.service.BaseService;

public interface CarTypeService extends BaseService<CarType, String>{
	
	
	public CarType findByCode(String code) throws Exception;
	
	public List<CarType> findAllData(String orderbyClause);
}
