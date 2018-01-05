package com.cs.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.common.constant.CacheConstant;
import com.cs.common.entityenum.AuthorizeType;
import com.cs.common.entityenum.ResourceType;
import com.cs.common.utils.RedisUtil;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;
import com.cs.system.dao.ResourceDao;
import com.cs.system.dao.RoleResourceRelationDao;
import com.cs.system.dao.UserResourceRelationDao;
import com.cs.system.entity.Resource;
import com.cs.system.entity.Role;
import com.cs.system.entity.RoleResourceRelation;
import com.cs.system.entity.User;
import com.cs.system.service.ResourceService;
import com.cs.system.service.RoleService;

 
@Service
@Transactional
public class ResourceServiceImpl  extends BaseServiceSupport<Resource, String> implements ResourceService{
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleResourceRelationDao roleResourceRelationDao;
	
	@Autowired
	private UserResourceRelationDao userResourceRelationDao;
	
	@Override
	protected BaseDao<Resource, String> getBaseDao() {
		return resourceDao;
	}

	@Override
	public List<Resource> findResourceByParentId(String parentId) throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		if(StringUtils.isNotBlank(parentId)) // 如果不为空，根据父节点查询子节点
		{
			sqlCondition.addSingleNotNullCriterion("PARENT_ID =", parentId);
		}else
		{
			sqlCondition.addCriterion("PARENT_ID is null");
		}
		List<String> orderbyClause = new ArrayList<String>();
		orderbyClause.add("order_num asc");
		sqlCondition.setOrderbyClause(orderbyClause);
		return resourceDao.findByCondition(sqlCondition);
	}

	@Override
	public int getResourceCountByParentId(String parentId) throws Exception{
		SqlCondition sqlCondition = new SqlCondition();
		if(StringUtils.isNotBlank(parentId)) // 如果不为空，根据父节点查询子节点
		{
			sqlCondition.addSingleNotNullCriterion("PARENT_ID =", parentId);
		} 
		Integer count  = resourceDao.countByCondition(sqlCondition);
		if(null == count)
		{
			return 0;
		} 
		return count.intValue();
	}

	@Override
	public List<Resource> findResourceByRoleId(String roleId) throws Exception{
		return resourceDao.findResourceByRoleId(roleId);
	}

	@Override
	public List<Resource> findResourceByUserId(String userId) throws Exception{
		return resourceDao.findResourceByUserId(userId);
	}

	@Override
	public void deleteResourceById(String resourceId) throws Exception{
		// 需要删除关联关系  然后删除本节点
		List<String> ids = new ArrayList<String>();
		ids.add(resourceId);
		recurseFindIds(ids, resourceId);
		//Collections.sort(ids);
		Collections.reverse(ids); // 因为list是有序的，所以先添加后删除
		for(String id : ids){
			// 删除角色资源关联表(RoleResourceRelation)
			 roleResourceRelationDao.deleteByResourceId(id);
			// 删除用户资源关联表(UserResourceRelation) 
			 userResourceRelationDao.deleteByResourceId(id);
			// 字元素也需要删掉 
			 resourceDao.deleteByPrimaryKey(id);
		}
		//删除资源,
		resourceDao.deleteByPrimaryKey(resourceId);
	}
	/**
	 * 获取当前资源下的所有子资源id
	 * @param parentId
	 * @return List<String>
	 */
	private List<String> getIds(String parentId) throws Exception
	{
		List<Resource> resourceList = this.findResourceByParentId(parentId);
		List<String> idsList = new ArrayList<String>();
		if(null != resourceList && resourceList.size() > 0)
		{
			for (int i = 0; i < resourceList.size(); i++) {
				idsList.add(resourceList.get(i).getId());
			}
		}
		return idsList;
	}
	
	/**
	 * 递归查询指定资源的所有子资源id
	 * @param ids
	 * @param parentId
	 * @return
	 */
	private void recurseFindIds(List<String> ids, String parentId) throws Exception{
		List<String> _ids = this.getIds(parentId);
		ids.addAll(_ids);
		if(_ids != null && _ids.size() > 0){
			for(String id : _ids){
				recurseFindIds(ids, id);
			}
		}
	}

	@Override
	public List<Resource> findResourceBy(String userId, String parentId, ResourceType resourceType) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("parentId", parentId);
	    if(null != resourceType )
	    {
	    	map.put("resourceType", resourceType.getId());
	    }
	    map.put("authorizeType", AuthorizeType.NORMAL.getId());
	    map.put("userId", userId);
		return resourceDao.findResourceBy(map);
	}

	@Override
	public int findResourceCountBy(String userId, String parentId,
			ResourceType resourceType) throws Exception{
		 List<Resource> resourceList = this.findResourceBy(userId, parentId, resourceType);
		 if(null != resourceList && resourceList.size() > 0)
		 {
			 return resourceList.size();
		 }
		return 0;
	}
	
	@Override
	public List<String> findAccessUrl(String userId, String parentId,
			ResourceType resourceType ,boolean isRefresh) throws Exception {
		List<String> accessList = null;
		if(!isRefresh){
			accessList = RedisUtil.getStrListValue(CacheConstant.USER_ACCESS_AUTHORITY_KEY + userId);
		}
		if (accessList == null || accessList.size() <= 0) {
			accessList = new ArrayList<String>();
			List<Resource> resources = this.findResourceBy(userId,parentId, resourceType);
			if (resources != null) {
				for (Resource resource : resources) {
					accessList.add(resource.getPath() + "&"+ resource.getMethodType());
				}
			}
			RedisUtil.delByKey(CacheConstant.USER_ACCESS_AUTHORITY_KEY+userId);//List保存不同，需先清除
			RedisUtil.setStrListValue(CacheConstant.USER_ACCESS_AUTHORITY_KEY+ userId, accessList, 1800);// 保存用户对应资源访问权限List
		}else{
			RedisUtil.expire(CacheConstant.USER_ACCESS_AUTHORITY_KEY, 1800);// 保存用户对应资源访问权限List
		}
		return accessList;
	}
	
	
	@Override
	public void addResource(Resource resource, User user) throws Exception {
		// 判断当前登陆者如果是管理员，那么为当前登陆者分配  当前添加的资源信息
		resourceDao.insert(resource);
		if(user != null)
		{
			if(null != user.getIsAdmin() && user.getIsAdmin() ==1)
			{
				// 获取当前用户的角色
				Role role = roleService.selectByPrimaryKey("e4e48584399473d20139947404a8002f"); // 本系统超级系统管理员
				if(role != null)
				{
					RoleResourceRelation relation = new RoleResourceRelation();
					relation.setRoleId(role.getId());
					relation.setResourceId(resource.getId());
					roleResourceRelationDao.insert(relation);
				}
			}
		}
	}
	
	
}
