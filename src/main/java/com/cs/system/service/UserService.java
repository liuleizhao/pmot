package com.cs.system.service;

import com.cs.mvc.service.BaseService;
import com.cs.system.entity.User;
import com.github.pagehelper.PageInfo;

public interface UserService extends BaseService<User, String>{

    /** 
     * 
     * @描述：根据账户名和密码获取用户
     * @作者：肖佳
     * @开发日期：2011-6-23 下午01:18:08
     * @param account  账户名
     * @param password  密码
     * @return
     */
    public User checkUser(String account, String password) throws Exception;

    /**
     * @描述：根据账户名获取用户
     * @作者：肖佳
     * @开发日期：2011-6-23 下午01:17:28
     * @param account 账户名
     * @return
     */
    public User findUserByAccount(String account) throws Exception;
    
    
    /**
     * @描述：根据用户名获取用户
     * @作者：盛强
     * @开发日期：2011-12-27
     * @param name 登录用户
     * @return 
     * @throws Exception
     */
    public User findByName(String name)throws Exception;
    
    /**
     * @描述：根据用户ID数组停用用户
     * @作者：邹伟坤
     * @开发日期：2011-12-27
     * @param name 登录用户
     * @return 
     * @throws Exception
     */
    public void blockUsers(String [] userIds) throws Exception;
    
    /**
	 * 根据角色Id查询
	 * @param roleId
	 * @return
	 * @Author        succ
	 * @date 2017-12-6 下午12:23:46
	 */
	public PageInfo<User> findByRole(String roleId, Integer pageNum, Integer pageSize) throws Exception;
	
	/**
	 * 通过角色分配权限
	 * @param userId
	 * @param newRoleIds
	 * @param oleRoleIds
	 */
	public void saveResourceByRoleIds(String userId,String[] newRoleIds,String[] oleRoleIds);
}
