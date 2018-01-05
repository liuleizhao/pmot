package com.cs.system.entity;

import java.util.Date;

import com.cs.common.entityenum.AuthorizeType;
import com.cs.mvc.dao.BaseEntity;

/**
 * 角色资源关联实体
 * @ClassName 	RoleResourceRelation
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-18	 下午2:18:13
 */
public class RoleResourceRelation extends BaseEntity{
	
    private AuthorizeType authorizeType = AuthorizeType.NORMAL;
 
    private String resourceId;
 
    private String roleId;
 
    private Date createdDate = new Date();
 
    public AuthorizeType getAuthorizeType() {
		return authorizeType;
	}

	public void setAuthorizeType(AuthorizeType authorizeType) {
		this.authorizeType = authorizeType;
	}

	public String getResourceId() {
        return resourceId;
    }
 
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
 
    public String getRoleId() {
        return roleId;
    }
 
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
 
    public Date getCreatedDate() {
        return createdDate;
    }
 
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}