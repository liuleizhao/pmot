package com.cs.argument.entity;

import java.util.Date;

import com.cs.mvc.dao.BaseEntity;

public class CarType extends BaseEntity{

	private static final long serialVersionUID = -9124684528077300914L;
	/** 代码*/
	private String code;

	/** 描述信息*/
    private String description;

    /** 名称*/
    private String name;

    /** 排列顺序*/
    private Short orderNum;

    /** 创建时间*/
    private Date createDate = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Short getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Short orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}