package com.cs.appoint.vo;

import java.io.Serializable;

public class ComApplyBookInfoVO implements Serializable{
	
	private static final long serialVersionUID = 3600040183777415002L;

	private String platNumber;
	
	private String carTypeId;
	
	private String bookDate;
	
	private String newflag;
	
	private String frameNumber;
	public String getPlatNumber() {
		return platNumber;
	}

	public void setPlatNumber(String platNumber) {
		this.platNumber = platNumber;
	}

	public String getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(String carTypeId) {
		this.carTypeId = carTypeId;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getNewflag() {
		return newflag;
	}

	public void setNewflag(String newflag) {
		this.newflag = newflag;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}
	
}
