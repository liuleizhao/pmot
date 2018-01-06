package com.cs.argument.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.argument.dao.BusBlackListConfigDao;
import com.cs.argument.entity.BusBlackListConfig;
import com.cs.argument.entity.CarType;
import com.cs.argument.service.BusBlackListConfigService;
import com.cs.common.entityenum.BookOperation;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class BusBlackListConfigServiceImpl  extends BaseServiceSupport<BusBlackListConfig, String> implements  BusBlackListConfigService {

	@Autowired
	private  BusBlackListConfigDao blackListConfigDao;
	@Override
	protected BaseDao<BusBlackListConfig, String> getBaseDao() throws Exception {
		return blackListConfigDao;
	}
	@Override
	public List<BusBlackListConfig> findByBookOperation(BookOperation cancel) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("OPERATION_TYPE =",cancel);
		List<BusBlackListConfig> blackListConfigs = blackListConfigDao.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(blackListConfigs))
		{
			return blackListConfigs;
		}
		return null;
	}

}
