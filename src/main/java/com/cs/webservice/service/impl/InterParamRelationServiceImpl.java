package com.cs.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.init.InitData;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.webservice.dao.InterParamRelationDao;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterParamRelationKey;
import com.cs.webservice.entity.InterfaceInfo;
import com.cs.webservice.service.InterParamRelationService;

@Service
@Transactional
public class InterParamRelationServiceImpl extends BaseServiceSupport<InterParamRelation, InterParamRelationKey> 
					implements InterParamRelationService{
	@Autowired
	private InterParamRelationDao interParamRelationDao;
	@Autowired
	private InitData initData;

	@Override
	protected BaseDao<InterParamRelation, InterParamRelationKey> getBaseDao() throws Exception {
		return interParamRelationDao;
	}

	@Override
	public int add(InterfaceInfo interfaceInfo,List<InterParamRelation> relationVOs) throws Exception {
		// 根据接口ID删除
		interParamRelationDao.deleteByInterfaceinfoId(interfaceInfo.getId());
		// 批量添加关系
		int addCount = 0;
		if(null != relationVOs && relationVOs.size() >0){
			for (InterParamRelation interParamRelation : relationVOs) {
				 System.out.println(interParamRelation.toString());
				 interParamRelationDao.insert(interParamRelation);
				 addCount++;
			}
		}
	    //int addCount = interParamRelationDao.batchAdd(relationVOs);
		initData.reloadInterfaceInfo();
		return addCount;
	}
	
}
