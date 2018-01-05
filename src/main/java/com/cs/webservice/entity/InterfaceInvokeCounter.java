package com.cs.webservice.entity;

import com.cs.mvc.dao.BaseEntity;

/**
 * 接口调用次数实体
 * @ClassName 	InterfaceInvokeCounter
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-28	 下午8:05:00
 */
public class InterfaceInvokeCounter extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/** 调用日期字符串 */
	private String invokeDate;
	/** 调用次数 */
	private int invokeCount;
	
	public String getInvokeDate() {
		return invokeDate;
	}
	public void setInvokeDate(String invokeDate) {
		this.invokeDate = invokeDate;
	}
	public int getInvokeCount() {
		return invokeCount;
	}
	public void setInvokeCount(int invokeCount) {
		this.invokeCount = invokeCount;
	}
}
