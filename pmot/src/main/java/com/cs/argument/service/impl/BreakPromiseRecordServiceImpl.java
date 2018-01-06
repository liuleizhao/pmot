package com.cs.argument.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.appoint.entity.BookInfo;
import com.cs.argument.dao.BreakPromiseRecordDao;
import com.cs.argument.entity.BreakPromiseRecord;
import com.cs.argument.service.BreakPromiseRecordService;
import com.cs.common.entityenum.BookOperation;
import com.cs.common.entityenum.BreakPromiseRecordState;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.dao.SqlCondition;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class BreakPromiseRecordServiceImpl extends BaseServiceSupport<BreakPromiseRecord, String> implements  BreakPromiseRecordService {

	@Autowired
	private BreakPromiseRecordDao breakPromiseRecordDao;
	
	@Override
	protected BaseDao<BreakPromiseRecord, String> getBaseDao() throws Exception {
		return breakPromiseRecordDao;
	}
	
	@Override
	public int addBreakPromise(BookInfo bookInfo,BookOperation bookOperation) {
		 List<BreakPromiseRecord> records = new ArrayList<BreakPromiseRecord>();
		 // 目前取消会将手机号码，号牌号码加入黑名单，具体多少次，看配置
		 // 手机号
		 BreakPromiseRecord mobileRecord = new BreakPromiseRecord();
		 mobileRecord.setName(bookInfo.getBookerName());
		 mobileRecord.setIdType(bookInfo.getIdTypeId());
		 mobileRecord.setIdNumber(bookInfo.getIdNumber());
		 mobileRecord.setBookNumber(bookInfo.getBookNumber());
		 mobileRecord.setRecordType(RecordType.MOBILE); // 手机号
		 mobileRecord.setRecordValue(bookInfo.getMobile());
		 mobileRecord.setBookOperation(bookOperation);
		 mobileRecord.setState(BreakPromiseRecordState.YX);
		 // 如果是失约
		 if(bookOperation.equals(BookOperation.BREAK_PROMISE)){
			 // 加入IP
			 /*BreakPromiseRecord ipRecord = new BreakPromiseRecord();
			 ipRecord.setName(bookInfo.getBookerName());
			 ipRecord.setIdType(bookInfo.getIdTypeId());
			 ipRecord.setIdNumber(bookInfo.getIdNumber());
			 ipRecord.setBookNumber(bookInfo.getBookNumber());
			 ipRecord.setRecordType(RecordType.IP); // 号牌号码
			 ipRecord.setRecordValue(bookInfo.getRequestIp());
			 ipRecord.setBookOperation(bookOperation);
			 ipRecord.setState(BreakPromiseRecordState.YX);
			 records.add(ipRecord);*/
			 
			 // 号牌号码
			/* BreakPromiseRecord platNumberRecord = new BreakPromiseRecord();
			 platNumberRecord.setName(bookInfo.getBookerName());
			 platNumberRecord.setIdType(bookInfo.getIdTypeId());
			 platNumberRecord.setIdNumber(bookInfo.getIdNumber());
			 platNumberRecord.setBookNumber(bookInfo.getBookNumber());
			 platNumberRecord.setRecordType(RecordType.PLATE_NUMBER); // 号牌号码
			 platNumberRecord.setRecordValue(bookInfo.getPlatNumber());
			 platNumberRecord.setBookOperation(bookOperation);
			 platNumberRecord.setState(BreakPromiseRecordState.YX);
			 records.add(platNumberRecord);*/
		 }
		 records.add(mobileRecord);
		 int addCount = breakPromiseRecordDao.batchAdd(records) ;
		 // 批量插入
		 return addCount;
	}

	@Override
	public List<BreakPromiseRecord> findBybookOperationAndRecordType(
			BookOperation bookOperation,RecordType recordType) throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleCriterion("RECORD_TYPE = ",recordType);
		sqlCondition.addSingleCriterion("BOOK_OPERATION = ",bookOperation);
		sqlCondition.addSingleCriterion("STATE = ",BreakPromiseRecordState.YX);
		List<BreakPromiseRecord> breakPromiseRecords = breakPromiseRecordDao.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(breakPromiseRecords)){ //存在
			return breakPromiseRecords;
		}
		return null;
	}

	@Override
	public List<BreakPromiseRecord> findByRecordValue(String recordValue)
			throws Exception {
		SqlCondition sqlCondition = new SqlCondition();
		sqlCondition.addSingleCriterion("RECORD_VALUE = ",recordValue);
		sqlCondition.addSingleCriterion("STATE = ",BreakPromiseRecordState.YX);
		List<BreakPromiseRecord> breakPromiseRecords = breakPromiseRecordDao.findByCondition(sqlCondition);
		if(CollectionUtils.isNotEmpty(breakPromiseRecords)){ //存在
			return breakPromiseRecords;
		}
		
		return null;
	}

	@Override
	public BreakPromiseRecord updateState(BreakPromiseRecord breakPromiseRecord) {
		breakPromiseRecord.setState(BreakPromiseRecordState.SX);
		breakPromiseRecordDao.updateByPrimaryKey(breakPromiseRecord);
		return breakPromiseRecord;
	}
	

}


