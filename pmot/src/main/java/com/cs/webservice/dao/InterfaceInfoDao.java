package com.cs.webservice.dao;

import com.cs.mvc.dao.BaseDao;
import com.cs.webservice.entity.InterfaceInfo;

/**
 * WebService接口信息表DAO
 * @ClassName:    InterfaceInfoDao
 * @Description:  
 * @Author        succ
 * @date          2017-10-28  上午10:29:33
 */
public interface InterfaceInfoDao extends BaseDao<InterfaceInfo,String> {
	public InterfaceInfo findByJkid(String jkid);
}
