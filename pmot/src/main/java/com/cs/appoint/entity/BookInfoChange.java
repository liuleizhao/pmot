package com.cs.appoint.entity;

import java.util.Date;

import com.cs.common.entityenum.BookState;
import com.cs.common.entityenum.CheckState;
import com.cs.mvc.dao.BaseEntity;

public class BookInfoChange extends BaseEntity{

	private static final long serialVersionUID = -3316747824896583582L;

	private String bookNumber;

    private BookState bookState;

    private Date checkTime;

    private String checkStation;

    private String checkSerialNumber;

    private Integer checkOperationMark;

    private CheckState checkState;
    
    private String frameNumber;

    private Date createDate;

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckStation() {
		return checkStation;
	}

	public void setCheckStation(String checkStation) {
		this.checkStation = checkStation;
	}

	public String getCheckSerialNumber() {
		return checkSerialNumber;
	}

	public void setCheckSerialNumber(String checkSerialNumber) {
		this.checkSerialNumber = checkSerialNumber;
	}

	public Integer getCheckOperationMark() {
		return checkOperationMark;
	}

	public void setCheckOperationMark(Integer checkOperationMark) {
		this.checkOperationMark = checkOperationMark;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public BookState getBookState() {
		return bookState;
	}

	public void setBookState(BookState bookState) {
		this.bookState = bookState;
	}

	public CheckState getCheckState() {
		return checkState;
	}

	public void setCheckState(CheckState checkState) {
		this.checkState = checkState;
	}
    
}