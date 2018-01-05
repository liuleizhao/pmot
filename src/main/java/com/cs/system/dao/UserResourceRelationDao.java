package com.cs.system.dao;

import com.cs.mvc.dao.BaseDao;
import com.cs.system.entity.UserResourceRelation;
public interface UserResourceRelationDao extends BaseDao<UserResourceRelation,String>{
	
    /**
     * 根据角色Id删除角色资源关联表
     * @param roleId 角色ID
     * 
     * */
	public int deleteByUserId(String userId);
	
    /**
     * 根据资源Id删除角色资源关联表
     * @param resourceId 资源ID
     * 
     * */
	public int deleteByResourceId(String resourceId);
	
}