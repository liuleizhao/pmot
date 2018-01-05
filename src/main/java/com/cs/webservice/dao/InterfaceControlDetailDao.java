package com.cs.webservice.dao;

import java.util.List;

import com.cs.mvc.dao.BaseDao;
import com.cs.webservice.entity.InterfaceControlDetail;


public interface InterfaceControlDetailDao extends BaseDao<InterfaceControlDetail,String>{
	public int deleteByGeneralId(String generalId);
	
	public int batchAdd(List<InterfaceControlDetail> interfaceControlDetails);
	
	/**
	 * 根据接口ID删除
	 * @param interfaceInfoId
	 * @return
	 */
	public int deleteByInterfaceInfoId(String interfaceInfoId);
}