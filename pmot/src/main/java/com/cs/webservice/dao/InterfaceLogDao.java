package com.cs.webservice.dao;

import com.cs.mvc.dao.BaseDao;
import com.cs.webservice.entity.InterfaceLog;

public interface InterfaceLogDao extends BaseDao<InterfaceLog,String> {
	/**
	 * 根据IP查询最新日志
	 * @param ip
	 * @return
	 * @throws Exception 
	 */
	public InterfaceLog findLatestByIp(String ip) throws Exception;
}