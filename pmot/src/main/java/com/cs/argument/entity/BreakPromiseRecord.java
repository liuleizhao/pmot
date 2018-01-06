package com.cs.argument.entity;

import java.util.Date;

import com.cs.common.entityenum.BookOperation;
import com.cs.common.entityenum.BreakPromiseRecordState;
import com.cs.common.entityenum.RecordType;
import com.cs.mvc.dao.BaseEntity;

public class BreakPromiseRecord extends BaseEntity{

	private static final long serialVersionUID = 187683544155553648L;

	/** 证件号 */
    private String idNumber;
    /** 证件类型 */
    private String idType;
	/** 姓名 */
    private String name;
	/** 预约号 */
    private String bookNumber;
    /** 记录类型【1：手机号；2：号牌号码；3：IP；4：账户】 */
    private RecordType recordType;
	/** 记录类型值 */
    private String recordValue;
	/** 记录来源 */
    private BookOperation bookOperation;
    /** 创建日期*/
    private Date  createDate = new Date();
    /** 失约记录状态*/
    private BreakPromiseRecordState state;


    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }


    public String getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(String recordValue) {
        this.recordValue = recordValue;
    }


    public BookOperation getBookOperation() {
		return bookOperation;
	}

	public void setBookOperation(BookOperation bookOperation) {
		this.bookOperation = bookOperation;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public BreakPromiseRecordState getState() {
		return state;
	}

	public void setState(BreakPromiseRecordState state) {
		this.state = state;
	}

}