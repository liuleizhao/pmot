package com.cs.system.dao;

import java.util.List;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.system.entity.Role;

public interface RoleDao extends BaseDao<Role, String> {

	public List<Role> findRoleByUserId(String userId);
	//超级管理员查询角色列表
	public List<Role> findRoleByAdmin(SqlCondition sqlcondition);
	
	public List<Role> findRoleByorgId(String orgId);
	
	

}