package com.cs2.veh.dao;

import java.util.List;

import com.cs.mvc.dao.SqlCondition;
import com.cs2.veh.entity.IVehIsFlow;

/**
 * 检验信息流水表DAO
 * @ClassName:    VehIsFlowDao
 * @Description:  
 * @Author        succ
 * @date          2017-11-6  上午10:49:43
 */
public interface IVehIsFlowDao {
	public IVehIsFlow selectByPrimaryKey(String lsh);
	
	public List<IVehIsFlow> findByCondition(SqlCondition sqlCondition);
}