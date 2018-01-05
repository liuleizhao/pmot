package com.cs.system.entity;

import java.util.Date;

import com.cs.mvc.dao.BaseEntity;

/**
 * 角色实体
 * @ClassName 	Role
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-15	 下午5:18:42
 */
public class Role extends BaseEntity{
	
	private static final long serialVersionUID = 927291789273270965L;

	/** 角色名称*/
    private String name;
    
    /** 角色描述 */
    private String description;
    
    /** 创建时间 */
    private Date createdDate = new Date();
    
    /** 创建者 */
    private String creator;
    
    /** 排序Num */
    private Long orderNum;
 
    /** 父类Id */
    private String parentId;
    
    /** 授权数 */
    private Integer count=0;
    
 
    public Date getCreatedDate() {
        return createdDate;
    }
 
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
 
    public String getCreator() {
        return creator;
    }
 
    public void setCreator(String creator) {
        this.creator = creator;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}