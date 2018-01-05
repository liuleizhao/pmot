package com.cs.argument.dao;

import java.util.List;

import com.cs.argument.entity.Org;
import com.cs.mvc.dao.BaseDao;

public interface OrgDao extends BaseDao<Org, String>{
	
	public List<Org> findBookOrgs(String businessTypeId);
	
}