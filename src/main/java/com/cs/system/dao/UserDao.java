package com.cs.system.dao;

import java.util.List;

import com.cs.mvc.dao.BaseDao;
import com.cs.system.entity.User;

public interface UserDao extends BaseDao<User,String>{
	/**
	 * 根据角色Id查询
	 * @param roleId
	 * @return
	 * @Author        succ
	 * @date 2017-12-6 下午12:23:46
	 */
	public List<User> findByRole(String roleId);
}