package com.cs.argument.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.argument.dao.BlackListRecordDao;
import com.cs.argument.entity.BlackListRecord;
import com.cs.argument.entity.BreakPromiseRecord;
import com.cs.argument.service.BlackListRecordService;
import com.cs.common.entityenum.BlacklistState;
import com.cs.common.entityenum.RecordType;
import com.cs.common.utils.DateUtil;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class BlackListRecordServiceImpl  extends BaseServiceSupport<BlackListRecord, String>  implements BlackListRecordService{

	@Autowired
	private BlackListRecordDao blackListRecordDao;
	@Override
	protected BaseDao<BlackListRecord, String> getBaseDao() throws Exception {
		return blackListRecordDao;
	}
	@Override
	public BlackListRecord addFromBreakPromise(
			Integer effectDays,List<BreakPromiseRecord> breakPromiseRecords) {
		BlackListRecord blackListRecord = new BlackListRecord();
		blackListRecord.setCreateDate(new Date());
		if(effectDays != null){
			blackListRecord.setEndDate(DateUtil.getDateAfter(new Date(),effectDays));
		}else {
			blackListRecord.setEndDate(null);
		}
		blackListRecord.setRecordType(breakPromiseRecords.get(0).getRecordType());
		blackListRecord.setRecordValue(breakPromiseRecords.get(0).getRecordValue());
		blackListRecord.setStatus(BlacklistState.SX);
		String bookNumbers = "";
		for (BreakPromiseRecord breakPromiseRecord : breakPromiseRecords) {
			bookNumbers += breakPromiseRecord.getBookNumber()+",";
		}
		blackListRecord.setBookNumbers(bookNumbers.substring(0, bookNumbers.length() -1));
		blackListRecordDao.insert(blackListRecord);

		return blackListRecord;
	}
	@Override
	public List<BlackListRecord> findByState(BlacklistState blacklistState) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		String nowDate = DateUtil.getDayStr(new Date());
		sqlCondition.addSingleCriterion("RECORD_TYPE = ",blacklistState);
		sqlCondition.addSingleCriterion("to_char(END_DATE,'YYYY-MM-DD') <= ",nowDate);
		List<BlackListRecord> blackListRecords = blackListRecordDao.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(blackListRecords)){ //存在
			return blackListRecords;
		}
		return null;
	}
	
	@Override
	public BlackListRecord findBlackListIsOpen(String value,RecordType recordType) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleCriterion("RECORD_TYPE = ",recordType);
		sqlCondition.addSingleCriterion("STATUS = ",BlacklistState.SX);
		sqlCondition.addSingleCriterion("RECORD_VALUE = ",value);
		List<BlackListRecord> blackListRecords = blackListRecordDao.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(blackListRecords)){ //存在
			return blackListRecords.get(0);
		}
		return null;
	}
	@Override
	public BlackListRecord checkBlackListByIdNumber(String idNumber) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		//sqlCondition.addSingleCriterion("RECORD_TYPE = ",RecordType.IDNUMBER);
		sqlCondition.addSingleCriterion("STATUS = ",BlacklistState.SX);
		sqlCondition.addSingleCriterion("RECORD_VALUE = ",idNumber);
		List<BlackListRecord> blackListRecords = blackListRecordDao.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(blackListRecords)){ //存在
			return blackListRecords.get(0);
		}
		
		return null;
	}


}
