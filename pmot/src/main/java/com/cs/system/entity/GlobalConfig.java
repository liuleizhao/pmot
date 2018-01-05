package com.cs.system.entity;

import java.util.Date;

import com.cs.mvc.dao.BaseEntity;

/**
 * 全局配置类
 * @ClassName:    GlobalConfig
 * @Description:  
 * @Author        succ
 * @date          2017-11-1  下午5:18:50
 */
public class GlobalConfig extends BaseEntity{
	private static final long serialVersionUID = 7576491690306489369L;


	/** 配置中文名 */
	private String name;

	/** 配置Key */
    private String dataKey;

    /** 配置Value */
    private String dataValue;

    private String description;

    private Date createDate;

    private Date modifyDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}