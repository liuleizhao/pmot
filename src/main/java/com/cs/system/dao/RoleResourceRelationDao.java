package com.cs.system.dao;

import com.cs.mvc.dao.BaseDao;
import com.cs.system.entity.RoleResourceRelation;
public interface RoleResourceRelationDao extends BaseDao<RoleResourceRelation,String>{
	
    /**
     * 根据角色Id删除角色资源关联表
     * @param roleId 角色Id
     * 
     * */
	public int deleteByRoleId(String roleId);
	
    /**
     * 根据资源Id删除角色资源关联表
     * @param resourceId 资源Id
     * 
     * */
	public void deleteByResourceId(String resourceId);
}