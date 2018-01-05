package com.cs.system.entity;

import java.util.Date;

import com.cs.common.entityenum.MethodType;
import com.cs.common.entityenum.ResourceType;
import com.cs.mvc.dao.BaseEntity;

/**
 * 资源实体
 * @ClassName 	Resource
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-15	 下午5:22:39
 */
public class Resource extends BaseEntity{

	private static final long serialVersionUID = 4762704860264440567L;

	private Date createdDate;

    private String creator;

    private String description;

    private String name;

    private Long orderNum;

    private String path;

    private MethodType methodType;

    private ResourceType resourceType;

    private String parentId;

    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MethodType getMethodType() {
		return methodType;
	}

	public void setMethodType(MethodType methodType) {
		this.methodType = methodType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}