package com.cs.argument.service;

import java.util.List;

import com.cs.argument.entity.Org;
import com.cs.mvc.service.BaseService;

public interface OrgService extends BaseService<Org, String>{
	

	
	// 查询开启的受理单位
	public List<Org> findOrgOpen() throws Exception;
	
	public Org findByCode(String code) throws Exception;
}
