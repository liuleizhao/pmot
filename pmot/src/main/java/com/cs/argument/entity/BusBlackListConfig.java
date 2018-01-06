package com.cs.argument.entity;

import com.cs.common.entityenum.BookOperation;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.dao.BaseEntity;

public class BusBlackListConfig extends BaseEntity{

	private static final long serialVersionUID = -1541995494882320413L;

	/** 预约操作类型*/
    private BookOperation operationType;

    /** 黑名单生效天数*/
    private Integer effectDays;
    
    /**  记录类型*/
    private RecordType recordType;
    
    /** 取消次数限制，超过限制次数被拉入失约；失约限制次数，超过限制次数被拉入黑名单*/
    private Integer limitCount;

	public BookOperation getOperationType() {
		return operationType;
	}

	public void setOperationType(BookOperation operationType) {
		this.operationType = operationType;
	}


	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public Integer getEffectDays() {
		return effectDays;
	}

	public void setEffectDays(Integer effectDays) {
		this.effectDays = effectDays;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

    

}