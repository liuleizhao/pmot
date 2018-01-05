package com.cs.webservice.entity;


public class InterParamRelation extends InterParamRelationKey{
	private static final long serialVersionUID = 1L;
	
	/**0 空  1不为空*/
	private Integer notNull;
	/** 参数下标*/
	private Integer orderIndex;
	/** 必须的属性，以逗号分隔*/
	private String requiredAttrs;
	/** 参数实体*/
	private InterfaceParameter parameter;
	

	public InterParamRelation() {
		super();
	}

	public Integer getNotNull() {
		return notNull;
	}

	public void setNotNull(Integer notNull) {
		this.notNull = notNull;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public InterfaceParameter getParameter() {
		return parameter;
	}

	public void setParameter(InterfaceParameter parameter) {
		this.parameter = parameter;
	}

	public String getRequiredAttrs() {
		return requiredAttrs;
	}

	public void setRequiredAttrs(String requiredAttrs) {
		this.requiredAttrs = requiredAttrs;
	}
	
}
