package com.cs.webservice.service;

import java.util.List;

import com.cs.mvc.service.BaseService;
import com.cs.webservice.entity.InterParamRelation;
import com.cs.webservice.entity.InterParamRelationKey;
import com.cs.webservice.entity.InterfaceInfo;

public interface InterParamRelationService extends BaseService<InterParamRelation,InterParamRelationKey>{
	
	public int add(InterfaceInfo interfaceInfo,List<InterParamRelation> relationVOs) throws Exception;
}
