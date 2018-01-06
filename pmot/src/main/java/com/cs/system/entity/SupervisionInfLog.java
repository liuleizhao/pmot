package com.cs.system.entity;

import java.util.Date;

import com.cs.common.entityenum.InterfaceInvokeType;
import com.cs.mvc.dao.BaseEntity;

public class SupervisionInfLog extends BaseEntity {

	private static final long serialVersionUID = -677134046343931528L;

	private String interfaceNum;

	private String requestXml;

	private String responseXml;

	private Date requestTime;

	private Date responseTime;

	private Integer runTime;
	
	/** 调用类型 */
    private InterfaceInvokeType invokeType;

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public String getInterfaceNum() {
		return interfaceNum;
	}

	public void setInterfaceNum(String interfaceNum) {
		this.interfaceNum = interfaceNum;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public InterfaceInvokeType getInvokeType() {
		return invokeType;
	}

	public void setInvokeType(InterfaceInvokeType invokeType) {
		this.invokeType = invokeType;
	}
}