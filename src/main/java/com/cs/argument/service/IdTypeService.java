package com.cs.argument.service;

import com.cs.argument.entity.IdType;
import com.cs.mvc.service.BaseService;

public interface IdTypeService extends BaseService<IdType, String>{
	public IdType findByCode(String code) throws Exception;
}
