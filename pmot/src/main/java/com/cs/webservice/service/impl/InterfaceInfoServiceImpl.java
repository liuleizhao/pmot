package com.cs.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.init.InitData;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.webservice.dao.InterParamRelationDao;
import com.cs.webservice.dao.InterfaceControlDetailDao;
import com.cs.webservice.dao.InterfaceInfoDao;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.service.InterfaceInfoService;

/**
 * WebService接口信息Service实现
 * @ClassName:    InterfaceInfoServiceImpl
 * @Description:  
 * @Author        succ
 * @date          2017-10-28  上午10:57:47
 */
@Service
@Transactional
public class InterfaceInfoServiceImpl extends BaseServiceSupport<InterfaceInfo,String> implements InterfaceInfoService {

	@Autowired
	private InterfaceInfoDao interfaceInfoDao;
	@Autowired
	private InterParamRelationDao interParamRelationDao;
	@Autowired
	private InterfaceControlDetailDao interfaceControlDetailDao;
	@Autowired
	private InitData initData;

	@Override
	protected BaseDao<InterfaceInfo, String> getBaseDao() throws Exception {
		return interfaceInfoDao;
	}

	@Override
	public List<InterParamRelation> findParameters(String id) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("INTERFACEINFO_ID = ", id);
		sqlCondition.addAscOrderbyColumn("ORDER_INDEX");
		return interParamRelationDao.findByCondition(sqlCondition);
	}

	@Override
	public List<InterfaceInfo> findAllOrderByJkIdAsc() throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addAscOrderbyColumn("JKID");
		return this.findByCondition(sqlCondition);
	}

	@Override
	public InterfaceInfo findByJkId(String jkId) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("JKID = ", jkId);
		return this.findUniqueByCondition(sqlCondition);
	}

	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		//先删除外键关联
		//接口方法与方法参数关联
		interParamRelationDao.deleteByInterfaceinfoId(id);
		//接口方法权限关联
		interfaceControlDetailDao.deleteByInterfaceInfoId(id);
		int result = super.deleteByPrimaryKey(id);
		initData.reloadInterfaceInfo();
		return result;
	}

	@Override
	public int insert(InterfaceInfo record) throws Exception {
		int result = super.insert(record);
		initData.reloadInterfaceInfo();
		return result;
	}

	@Override
	public int insertSelective(InterfaceInfo record) throws Exception {
		int result = super.insertSelective(record);
		initData.reloadInterfaceInfo();
		return result;
	}

	@Override
	public int updateByPrimaryKeySelective(InterfaceInfo record)
			throws Exception {
		int result = super.updateByPrimaryKeySelective(record);
		initData.reloadInterfaceInfo();
		return result;
	}

	@Override
	public int updateByPrimaryKey(InterfaceInfo record) throws Exception {
		int result = super.updateByPrimaryKey(record);
		initData.reloadInterfaceInfo();
		return result;
	}
	
	
}
