package com.cs.webservice.service;

import java.util.List;

import com.cs.mvc.service.BaseService;
import com.cs.webservice.entity.InterfaceControlDetail;

public interface InterfaceControlDetailService extends BaseService<InterfaceControlDetail, String>{
	public List<InterfaceControlDetail> findByGeneralId(String generalId) throws Exception;
	
	public int deleteByGeneralId(String generalId) throws Exception;
	
	public int batchAdd(List<InterfaceControlDetail> list) throws Exception;
}
