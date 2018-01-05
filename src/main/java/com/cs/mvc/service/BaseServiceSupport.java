package com.cs.mvc.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * BaseService泛型基类
 * @author vincent
 *
 * @param <T>
 * @param <PK>
 */
@Transactional
public abstract class BaseServiceSupport<T, PK> implements BaseService<T, PK> {

	  /** 当前页 */
    protected Integer pageNum = 1;

    /** 每页显示的记录数 */
    protected Integer pageSize = 12;
    
	
	@Override
	public int deleteByPrimaryKey(PK id) throws Exception{
		return this.getBaseDao().deleteByPrimaryKey(id);
	}

	@Override
	public int insert(T record) throws Exception{

		return this.getBaseDao().insert(record);
	}

	@Override
	public int insertSelective(T record) throws Exception{
		return this.getBaseDao().insertSelective(record);
	}

	@Override
	public T selectByPrimaryKey(PK id) throws Exception{

		return this.getBaseDao().selectByPrimaryKey(id);
	}

	@Override
	public List<T> selectBySelective(T record) throws Exception{
		return this.getBaseDao().selectBySelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(T record) throws Exception{
		return this.getBaseDao().updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(T record) throws Exception{
		return this.getBaseDao().updateByPrimaryKey(record);
	}

	@Override
	public List<T> findAllData() throws Exception{
		return this.getBaseDao().findAllData();
	}

	@Override
	public List<T> findByCondition(SqlCondition sqlCondition) throws Exception{
		return this.getBaseDao().findByCondition(sqlCondition);
	}

	@Override
	public Integer countByCondition(SqlCondition sqlCondition) throws Exception{
		return this.getBaseDao().countByCondition(sqlCondition);
	}
	
	@Override
	public PageInfo<T> findByCondition(SqlCondition sqlCondition ,Integer pageNum , Integer pageSize) throws Exception{
		
		if(pageNum ==null || pageNum <1){
			pageNum = this.pageNum;
		}
		
		if(pageSize == null || pageSize <1){
			pageSize = this.pageSize;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = this.findByCondition(sqlCondition);
		PageInfo<T> page = new PageInfo<T>(list);
		return page;
	}
	
	@Override
	public T findUniqueByCondition(SqlCondition sqlCondition) throws Exception {

		List<T> list =  findByCondition(sqlCondition);
		
		if(list ==null || list.size()<1){
			return null;
		}
		
		if(list.size()==1){
			return list.get(0);
		}
		
		throw new Exception("查询结果不唯一!");
		 
	}

	protected abstract BaseDao<T, PK> getBaseDao() throws Exception;

}
