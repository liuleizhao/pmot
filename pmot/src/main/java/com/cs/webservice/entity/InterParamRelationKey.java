package com.cs.webservice.entity;

import java.io.Serializable;

public class InterParamRelationKey implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	/** 接口Id*/
	private String interfaceInfoId;
	/** 参数Id*/
	private String parameterId;
	
	public String getInterfaceInfoId() {
		return interfaceInfoId;
	}
	public void setInterfaceInfoId(String interfaceInfoId) {
		this.interfaceInfoId = interfaceInfoId;
	}
	public String getParameterId() {
		return parameterId;
	}
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	
}
