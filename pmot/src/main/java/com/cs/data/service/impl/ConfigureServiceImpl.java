package com.cs.data.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.common.entityenum.ExportState;
import com.cs.data.dao.ExportDao;
import com.cs.data.entity.Export;
import com.cs.data.service.ConfigureService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class ConfigureServiceImpl extends BaseServiceSupport<Export, String> implements ConfigureService{

	@Autowired
	private ExportDao exportDao; 
	
	@Override
	protected BaseDao<Export, String> getBaseDao() throws Exception {
		return exportDao;
	}

	@Override
	public boolean checkTableName(String tableName) throws Exception{
		
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("TABLE_NAME = ", tableName);
		List<Export> list = exportDao.findByCondition(sqlCondition);
		if(list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public List<String> findTableName() {
		return exportDao.findTableName();
	}

	@Override
	public List<String> findTableCol(String tableName) {
		return exportDao.findTableCol(tableName);
	}

	@Override
	public List<Export> findEnable() throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("state = ", ExportState.ENABLE.getId());
		return exportDao.findByCondition(sqlCondition);
	}
}
