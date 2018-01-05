package com.cs.mvc.dao;

import java.util.List;
/**
 * DAO通用接口
 * @author vincent
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseDao<T, PK> {

	public Integer deleteByPrimaryKey(PK id);

	public Integer insert(T record);

	public Integer insertSelective(T record);
	
	public T selectByPrimaryKey(PK id);

	public List<T> selectBySelective(T record);
	
	public Integer updateByPrimaryKeySelective(T record);

	public Integer updateByPrimaryKey(T record);

	public List<T> findAllData();
	
	public List<T> findByCondition(SqlCondition sqlCondition);
	
	public Integer countByCondition(SqlCondition sqlCondition);
}
