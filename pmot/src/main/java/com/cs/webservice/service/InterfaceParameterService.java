package com.cs.webservice.service;

import com.cs.mvc.service.BaseService;
import com.cs.webservice.entity.InterfaceParameter;

public interface InterfaceParameterService extends BaseService<InterfaceParameter,String>{
	public InterfaceParameter findByNameAndType(String name,String type) throws Exception;
}
