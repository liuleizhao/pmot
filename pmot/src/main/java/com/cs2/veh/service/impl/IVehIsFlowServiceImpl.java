package com.cs2.veh.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.common.utils.DateUtils;
import com.cs.mvc.dao.SqlCondition;
import com.cs2.veh.dao.IVehIsFlowDao;
import com.cs2.veh.entity.IVehIsFlow;
import com.cs2.veh.service.IVehIsFlowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class IVehIsFlowServiceImpl implements IVehIsFlowService {

	@Autowired
	private IVehIsFlowDao ivehIsFlowDao;
	
	@Override
	public IVehIsFlow selectByPrimaryKey(String lsh) {
		return ivehIsFlowDao.selectByPrimaryKey(lsh);
	}

	@Override
	public List<IVehIsFlow> findByCondition(SqlCondition sqlCondition)
			throws Exception {
		return ivehIsFlowDao.findByCondition(sqlCondition);
	}

	@Override
	public List<IVehIsFlow> findTodays() throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("to_char(CJSJ,'yyyy-mm-dd') = ", DateUtils.getDate());
		return this.findByCondition(sqlCondition);
	}

	@Override
	public PageInfo<IVehIsFlow> findByCondition(SqlCondition sqlCondition,
			Integer pageNum, Integer pageSize) throws Exception {
		if(pageNum ==null || pageNum <1){
			pageNum = 1;
		}
		
		if(pageSize == null || pageSize <1){
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<IVehIsFlow> list = this.findByCondition(sqlCondition);
		PageInfo<IVehIsFlow> page = new PageInfo<IVehIsFlow>(list);
		return page;
	}

	@Override
	public List<IVehIsFlow> findByDate(Date cjsj) throws Exception {
		if(cjsj == null){
			throw new Exception("创建日期不能为空！");
		}
		String dateStr = DateUtils.getDate(cjsj);
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleNotNullCriterion("to_char(CJSJ,'yyyy-mm-dd') = ", dateStr);
		return findByCondition(sqlCondition);
	}
}
