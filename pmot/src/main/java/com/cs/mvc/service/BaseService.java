package com.cs.mvc.service;

import java.util.List;

import com.cs.mvc.dao.SqlCondition;
import com.github.pagehelper.PageInfo;

/**
 * Service通用接口
 * @author vincent
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseService <T, PK>{
	public int deleteByPrimaryKey(PK id) throws Exception;

	public int insert(T record) throws Exception;

	public int insertSelective(T record) throws Exception;
	
	public T selectByPrimaryKey(PK id) throws Exception;

	public List<T> selectBySelective(T record) throws Exception;
	
	public int updateByPrimaryKeySelective(T record) throws Exception;

	public int updateByPrimaryKey(T record) throws Exception;

	public List<T> findAllData() throws Exception;
	
	public List<T> findByCondition(SqlCondition sqlCondition) throws Exception;
	
	public Integer countByCondition(SqlCondition sqlCondition) throws Exception;
	
	public PageInfo<T> findByCondition(SqlCondition sqlCondition ,Integer pageNum , Integer pageSize) throws Exception;

	public T findUniqueByCondition(SqlCondition sqlCondition) throws Exception;
}
