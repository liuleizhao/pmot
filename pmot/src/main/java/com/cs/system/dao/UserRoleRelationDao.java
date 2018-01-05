package com.cs.system.dao;

import java.util.List;

import com.cs.mvc.dao.BaseDao;
import com.cs.system.entity.UserRoleRelation;

public interface UserRoleRelationDao extends BaseDao<UserRoleRelation, String> {
	  /**
     * 根据角色Id删除角色资源关联表
     * @param roleId 角色ID
     * 
     * */
	public int deleteByRoleId(String roleId);
	
    /**
     * 根据用户Id删除用户资源关联表
     * @param roleId 用户Id
     * 
     * */
	public int deleteByuserId(String userId);
	
	 /**
     * 根据用户Id查找角色
     * @param roleId 用户Id
     * 
     * */
	public List<UserRoleRelation> selectroleByuserId(String userId);
	
	/**
     * 根据用户Id和角色删除用户角色关联表
     * @param roleId 用户Id
     * 
     * */
	public int deleteByuserIdAndRoleId(String userId,String roleId);
	
	
}