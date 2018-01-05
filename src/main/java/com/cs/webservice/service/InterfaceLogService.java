package com.cs.webservice.service;

import com.cs.mvc.service.BaseService;
import com.cs.webservice.entity.InterfaceLog;

public interface InterfaceLogService extends BaseService<InterfaceLog, String>{
	/**
	 * 根据IP查询最新日志
	 * @param ip
	 * @return
	 * @throws Exception 
	 */
	public InterfaceLog findLatestByIp(String ip) throws Exception;
}
