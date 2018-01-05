package com.cs.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.system.dao.RoleDao;
import com.cs.system.dao.RoleResourceRelationDao;
import com.cs.system.dao.UserRoleRelationDao;
import com.cs.system.entity.Role;
import com.cs.system.entity.RoleResourceRelation;
import com.cs.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
@Transactional
public class RoleServiceImpl extends BaseServiceSupport<Role, String> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleResourceRelationDao roleResourceRelationDao;

	@Autowired
	private UserRoleRelationDao userRoleRelationDao;   
   
	@Override
	protected  BaseDao<Role, String> getBaseDao() {
		return roleDao;
	}

	@Override
	public Role findByName(String name)  throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("name =", name);
		List<Role> roles = roleDao.findByCondition(sqlCondition);
		if(null != roles && roles.size() > 0)
		{
			return roles.get(0);
		}
		return null;
	}

	@Override
	public void saveRoleAndRelation(String[] ids, Role role)  throws Exception{
		this.insert(role);
		if(ids!=null && ids.length > 0)
		{
	        for(String id : ids){
	        	RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
	            roleResourceRelation.setRoleId(role.getId());
	            roleResourceRelation.setResourceId(id);
	            roleResourceRelationDao.insert(roleResourceRelation);
	        }
		}
	}

	@Override
	public void updateRoleAndRelation(String[] ids, Role role)  throws Exception{
		// 修改
		this.updateByPrimaryKeySelective(role);
		// 删除角色删除原有绑定的权限  --角色资源关系表
		roleResourceRelationDao.deleteByRoleId(role.getId());
		if(ids!=null && ids.length > 0)
		{
			// 重新绑定权限
	        for(String id : ids){
	        	RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
	            roleResourceRelation.setRoleId(role.getId());
	            roleResourceRelation.setResourceId(id);
	            roleResourceRelationDao.insert(roleResourceRelation);
	        }
		}
	}

	@Override
	public void deleteById(String roleId)  throws Exception{
		//删除角色资源关联表
		roleResourceRelationDao.deleteByRoleId(roleId);
		//删除角色用户关联表
		userRoleRelationDao.deleteByRoleId(roleId);
		//删除角色
	    roleDao.deleteByPrimaryKey(roleId);
	}

	@Override
	public List<Role> findRoleByUserId(String userId)  throws Exception{
		if(StringUtils.isNotBlank(userId))
		{
			return roleDao.findRoleByUserId(userId);
		}
		return null;
		
	}

	@Override
	public List<Role> findRoleByAdmin(SqlCondition sqlcondition) throws Exception {
		
		return roleDao.findRoleByAdmin(sqlcondition);
	}
	

	@Override
	public PageInfo<Role> findRoleByAdmin(SqlCondition sqlCondition,
			Integer pageNum, Integer pageSize) throws Exception {
		if(pageNum ==null || pageNum <1){
			pageNum = this.pageNum;
		}
		
		if(pageSize == null || pageSize <1){
			pageSize = this.pageSize;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list = roleDao.findRoleByAdmin(sqlCondition);
		PageInfo<Role> page = new PageInfo<Role>(list);
		return page;
	}

	@Override
	public List<Role> findRoleByorgId(String orgId) throws Exception {
		return roleDao.findRoleByorgId(orgId);
	}
 
}