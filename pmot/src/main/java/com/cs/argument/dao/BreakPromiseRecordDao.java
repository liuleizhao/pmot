package com.cs.argument.dao;

import java.util.List;

import com.cs.argument.entity.BreakPromiseRecord;
import com.cs.mvc.dao.BaseDao;

public interface BreakPromiseRecordDao  extends BaseDao<BreakPromiseRecord, String>{
	
	public int batchAdd(List<BreakPromiseRecord> records);
}