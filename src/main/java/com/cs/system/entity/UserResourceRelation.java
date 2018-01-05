package com.cs.system.entity;

import java.util.Date;

import com.cs.common.entityenum.AuthorizeType;
import com.cs.mvc.dao.BaseEntity;


public class UserResourceRelation  extends BaseEntity{
 
    private AuthorizeType authorizeType = AuthorizeType.NORMAL; 
 
    private String resourceId;
 
    private String userId;
 
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
 
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
 
    public Date getCreatedDate() {
        return createdDate;
    }
 
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}