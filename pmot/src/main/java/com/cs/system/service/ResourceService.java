package com.cs.system.service;

import java.util.List;

import com.cs.common.entityenum.ResourceType;
import com.cs.mvc.service.BaseService;
import com.cs.system.entity.Resource;
import com.cs.system.entity.User;


public interface ResourceService extends BaseService<Resource,String>{
	
	public List<Resource> findResourceByParentId(String parentId) throws Exception;
	
	public int getResourceCountByParentId(String parentId) throws Exception;
	
	public List<Resource> findResourceByRoleId(String roleId) throws Exception;
	
	public List<Resource> findResourceByUserId(String userId) throws Exception;
	
	public void deleteResourceById(String resourceId) throws Exception;
	
	public List<Resource> findResourceBy(String userId,String parentId,ResourceType resourceType) throws Exception;
	
	public int findResourceCountBy(String userId,String parentId,ResourceType resourceType) throws Exception;
	
	/**
	 * 
	 * @param userId
	 * @param parentId
	 * @param resourceType
	 * @param isRefresh  是否强制刷新
	 * @return
	 * @throws Exception
	 */
	public List<String> findAccessUrl(String userId, String parentId,ResourceType resourceType, boolean isRefresh) throws Exception ;
	
	public void addResource(Resource resource ,User user) throws Exception;
	
}
