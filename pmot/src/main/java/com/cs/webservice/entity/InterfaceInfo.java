package com.cs.webservice.entity;

import java.util.List;

import com.cs.common.entityenum.Recordable;
import com.cs.mvc.dao.BaseEntity;
/**
 * 对外接口的接口ID表
 * @author HYL
 *
 */
public class InterfaceInfo extends BaseEntity{
	//id ,接口ID，描述，方法名

	private static final long serialVersionUID = -606216661241596304L;

	/** 对应类名*/
	private String className;
	
	/** 方法名*/
	private String methodName;
	
	/** 接口描述*/
	private String description;
	
	/** 是否记录日志 1-记录 0-不记录 */
	private Recordable recordable;
	
	/** 接口名称 */
	private String name;
	
	/** 接口代码 */
	private String jkid;
	
	/** 方法所有参数*/
	private List<InterParamRelation> params;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<InterParamRelation> getParams() {
		return params;
	}

	public void setParams(List<InterParamRelation> params) {
		this.params = params;
	}

	public Recordable getRecordable() {
		return recordable;
	}

	public void setRecordable(Recordable recordable) {
		this.recordable = recordable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJkid() {
		return jkid;
	}

	public void setJkid(String jkid) {
		this.jkid = jkid;
	}

	
	
}
