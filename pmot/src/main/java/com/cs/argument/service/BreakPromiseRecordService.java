package com.cs.argument.service;

import java.util.List;

import com.cs.appoint.entity.BookInfo;
import com.cs.argument.entity.BreakPromiseRecord;
import com.cs.common.entityenum.BookOperation;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.service.BaseService;

public interface BreakPromiseRecordService  extends  BaseService<BreakPromiseRecord, String>{
	
	public int addBreakPromise(BookInfo bookInfo,BookOperation bookOperation);
	
	public List<BreakPromiseRecord> findBybookOperationAndRecordType(BookOperation bookOperation,RecordType recordType) throws Exception ;

	public List<BreakPromiseRecord> findByRecordValue(String recordValue)  throws Exception;

	public BreakPromiseRecord updateState(BreakPromiseRecord breakPromiseRecord);
}
