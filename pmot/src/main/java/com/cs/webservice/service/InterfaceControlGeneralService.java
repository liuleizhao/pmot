package com.cs.webservice.service;

import com.cs.mvc.service.BaseService;
import com.cs.webservice.entity.InterfaceControlGeneral;

public interface InterfaceControlGeneralService extends BaseService<InterfaceControlGeneral, String>{
	
	/**
	 * 根据IP地址查询
	 * @param ip
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-10 上午10:49:42
	 */
	public InterfaceControlGeneral findByIp(String ip) throws Exception;
	
	/**
	 * 根据序列号查询
	 * @param serialNumber
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-21 下午4:48:48
	 */
	public InterfaceControlGeneral findBySerialNumber(String serialNumber) throws Exception;
	
	public InterfaceControlGeneral findByStationId(String stationId) throws Exception;
}
