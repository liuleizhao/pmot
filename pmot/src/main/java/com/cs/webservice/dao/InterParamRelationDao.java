package com.cs.webservice.dao;

import com.cs.mvc.dao.BaseDao;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterParamRelationKey;

public interface InterParamRelationDao extends BaseDao<InterParamRelation,InterParamRelationKey> {
	/**
	 * 删除接口ID与对应的参数的联系
	 * @param interfaceinfoId
	 * @return
	 * @Author        succ
	 * @date 2017-11-21 下午2:45:59
	 */
	public int deleteByInterfaceinfoId(String interfaceinfoId);
}
