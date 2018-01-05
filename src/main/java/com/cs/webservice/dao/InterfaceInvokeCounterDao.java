package com.cs.webservice.dao;

import com.cs.mvc.dao.BaseDao;
import com.cs.webservice.entity.InterfaceInvokeCounter;

public interface InterfaceInvokeCounterDao extends BaseDao<InterfaceInvokeCounter, String> {
	/**
	 * 给指定日期增一
	 * @param invokeDate
	 * @return
	 */
	public int plusOneByInvokeDate(String invokeDate);
}