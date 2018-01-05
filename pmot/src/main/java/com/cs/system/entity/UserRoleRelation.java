package com.cs.system.entity;

import java.util.Date;

import com.cs.common.entityenum.AuthorizeType;
import com.cs.mvc.dao.BaseEntity;


public class UserRoleRelation  extends BaseEntity{
 
    private AuthorizeType authorizeType = AuthorizeType.NORMAL;
 
    private String roleId;
 
    private String userId;
 
    private Date createdDate = new Date();
    
    private String userName;
 
    public AuthorizeType getAuthorizeType() {
		return authorizeType;
	}

	public void setAuthorizeType(AuthorizeType authorizeType) {
		this.authorizeType = authorizeType;
	}

	public String getRoleId() {
        return roleId;
    }
 
    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
    
   
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}