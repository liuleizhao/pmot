package com.cs.system.service;


import java.util.List;

import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseService;
import com.cs.system.entity.Role;
import com.github.pagehelper.PageInfo;


public interface RoleService  extends BaseService<Role, String>{
	
	/** 根据角色名称查询是否存在
	 * @param name  名称
	 * @param   
	 * 
	 * */
	public Role findByName(String name) throws Exception;
	
	/** 保存角色
	 * @param ids  资源IDs
	 * @param Role  角色信息
	 * @param   
	 * 
	 * */
	public void saveRoleAndRelation (String [] ids ,Role role) throws Exception;
	
	/** 修改角色信息，重新绑定权限
	 * @param ids  资源IDs
	 * @param Role  角色信息
	 * 
	 * */
	public void updateRoleAndRelation(String [] ids ,Role role) throws Exception;
	
	/** 删除角色信息
	 * @param id 角色Id
	 * 
	 * */
	public void deleteById (String roleId)  throws Exception;
	
	/** 根据用户id找角色信息
	 * @param userId 用户Id
	 * 
	 * */
	public List<Role> findRoleByUserId(String userId) throws Exception;
	
	/** 根据组织id查找角色信息
	 * @param orgId 角色ID
	 * 
	 * */
	public List<Role> findRoleByorgId(String orgId)throws Exception;
	
	
	/** 超级管理员查询角色列表
	 * @param sqlcondition 查询条件
	 * 
	 * */
	public List<Role> findRoleByAdmin(SqlCondition sqlcondition)throws Exception;
	
	public PageInfo<Role> findRoleByAdmin(SqlCondition sqlCondition ,Integer pageNum , Integer pageSize) throws Exception;
}
