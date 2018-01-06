package com.cs.argument.entity;

import java.util.Date;

import com.cs.common.entityenum.BlacklistState;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.dao.BaseEntity;

public class BlackListRecord extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2173150682175979834L;

	/** 黑名单状态 */
	private BlacklistState status;
	
	/** 记录类型 */
    private RecordType recordType;
    
	/** 记录值 */
    private String recordValue;
    
    /** 创建日期 */
    private Date createDate;
    
	/** 结束日期 */
    private Date endDate;

    /** 导致拉入黑名单的预约号，多个使用','分开*/
    private String bookNumbers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(String recordValue) {
        this.recordValue = recordValue;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBookNumbers() {
        return bookNumbers;
    }

    public void setBookNumbers(String bookNumbers) {
        this.bookNumbers = bookNumbers;
    }

	public BlacklistState getStatus() {
		return status;
	}

	public void setStatus(BlacklistState status) {
		this.status = status;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}
    
}