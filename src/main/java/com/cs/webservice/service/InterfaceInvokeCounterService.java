package com.cs.webservice.service;

import com.cs.mvc.service.BaseService;
import com.cs.webservice.entity.InterfaceInvokeCounter;

public interface InterfaceInvokeCounterService extends BaseService<InterfaceInvokeCounter, String> {
	/**
	 * 根据调用日期查询
	 * @param invokeDate
	 * @return
	 * @throws Exception 
	 */
	public InterfaceInvokeCounter findByInvokeDate(String invokeDate) throws Exception; 
	
	/**
	 * 当前日期的接口调用次数增一
	 * @throws Exception 
	 */
	public void plusOne() throws Exception;
}
