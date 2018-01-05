package com.cs.appoint.service;

import com.cs.appoint.entity.VehIsInfo;
import com.cs.argument.entity.Station;
import com.cs.mvc.service.BaseService;

public interface VehIsInfoService extends BaseService<VehIsInfo, String>{
	
	
	public String getVehIsInfos(Station station) throws Exception ;
		
	public String sendXml(String platNumber, String carTypeCode,
			String chassisNumber, String stionNum ) throws Exception;
}
