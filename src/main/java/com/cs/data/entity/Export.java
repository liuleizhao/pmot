package com.cs.data.entity;

import java.util.Date;

import com.cs.common.entityenum.ExportState;
import com.cs.mvc.dao.BaseEntity;

public class Export extends BaseEntity{

	private static final long serialVersionUID = -6763820269010292384L;

	private String tableName;

    private String field;

    private String condition;

    private ExportState state;

    private int order;

    private String description;

    private Date createDate;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public ExportState getState() {
        return state;
    }

    public void setState(ExportState state) {
        this.state = state;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
}