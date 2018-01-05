package com.cs.system.dao;

import java.util.List;
import java.util.Map;
import com.cs.mvc.dao.BaseDao;
import com.cs.system.entity.Resource;

public interface ResourceDao extends BaseDao<Resource , String> {

	public List<Resource> findResourceByRoleId(String roleId);
	
	public List<Resource> findResourceByUserId(String userId);
	
	public List<Resource> findResourceBy(Map<String, Object> map);
	
}