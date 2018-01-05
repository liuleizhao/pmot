package com.cs.webservice.entity;

import com.cs.mvc.dao.BaseEntity;

public class InterfaceParameter extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/** 名称*/
	private String name;
	
	/** 类型:基本数据类型，VO*/
	private String type;
	
	private String description;
	
	
	public InterfaceParameter(String name,String type,String description){
		this.name = name;
		this.type = type;
		this.description = description;
	}
	
	public InterfaceParameter()
	{
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
