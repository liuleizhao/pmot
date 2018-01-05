package com.cs.argument.service;


import java.util.List;

import com.cs.argument.entity.Station;
import com.cs.mvc.service.BaseService;


public interface StationService  extends BaseService<Station, String>{
	
	public Station findByCodeOrName(Integer type ,String codeOrName,String stationId) throws Exception;
	public List<Station> findStationsByDistrict(String districtId)  throws Exception;
	
	public List<Station> findStationOpen() throws Exception;
	
	/**
	 * 根据检测站编号查询
	 * @param code
	 * @return
	 * @throws Exception 
	 * @Author        succ
	 * @date 2017-11-1 下午6:42:41
	 */
	public Station findByCode(String code) throws Exception;

	public Station findByName(String name) throws Exception;
	
	/**
	 * 查询没有配置接口权限的检测站
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-24 下午12:20:45
	 */
	public List<Station> findNotExistInterfaceControl() throws Exception;
	
	int batchUpdateMaxNumber(List<Station> stations);
}
