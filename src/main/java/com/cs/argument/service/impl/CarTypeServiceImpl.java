package com.cs.argument.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.argument.dao.CarTypeDao;
import com.cs.argument.entity.CarType;
import com.cs.argument.service.CarTypeService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class CarTypeServiceImpl extends BaseServiceSupport<CarType, String>  implements CarTypeService{
	@Autowired
	private CarTypeDao carTypeDao;
	
	@Override
	protected  BaseDao<CarType, String> getBaseDao() throws Exception{
		return carTypeDao;
	}
	
	@Override
	@Transactional
	public CarType findByCode(String code) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("code =",code);
		List<CarType> caTypeList= carTypeDao.findByCondition(sqlCondition);
		if(null != caTypeList && caTypeList.size() > 0)
		{
			return caTypeList.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public List<CarType> findAllData(String orderbyClause) {
		// TODO Auto-generated method stub
		return carTypeDao.findAllDataByOrder(orderbyClause);
	}
	
}
