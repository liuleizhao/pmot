package com.cs.argument.service;

import java.util.List;

import com.cs.argument.entity.BlackListRecord;
import com.cs.argument.entity.BreakPromiseRecord;
import com.cs.common.entityenum.BlacklistState;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.service.BaseService;

public interface BlackListRecordService  extends BaseService<BlackListRecord, String>{

	BlackListRecord addFromBreakPromise(Integer effectDays,List<BreakPromiseRecord> breakPromiseRecords);
	
	List<BlackListRecord> findByState(BlacklistState blacklistState) throws Exception ;

	BlackListRecord findBlackListIsOpen(String value,RecordType recordType) throws Exception;
	
	BlackListRecord checkBlackListByIdNumber(String idNumber) throws Exception;

}
