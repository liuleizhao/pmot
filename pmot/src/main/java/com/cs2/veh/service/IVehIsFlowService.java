package com.cs2.veh.service;

import java.util.Date;
import java.util.List;

import com.cs.mvc.dao.SqlCondition;
import com.cs2.veh.entity.IVehIsFlow;
import com.github.pagehelper.PageInfo;

public interface IVehIsFlowService {
	public IVehIsFlow selectByPrimaryKey(String lsh);
	
	public List<IVehIsFlow> findByCondition(SqlCondition sqlCondition) throws Exception;
	
	public PageInfo<IVehIsFlow> findByCondition(SqlCondition sqlCondition ,Integer pageNum , Integer pageSize) throws Exception;
	
	public List<IVehIsFlow> findTodays() throws Exception;
	
	public List<IVehIsFlow> findByDate(Date cjsj) throws Exception;
}
