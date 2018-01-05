package com.cs.argument.entity;

import com.cs.mvc.dao.BaseEntity;

public class IdType extends BaseEntity{
	private static final long serialVersionUID = 5438872867785514838L;

    private String code;

    private String name;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}