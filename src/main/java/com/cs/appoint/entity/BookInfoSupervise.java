package com.cs.appoint.entity;

import java.util.Date;

import com.cs.common.entityenum.BookState;

public class BookInfoSupervise {
	
	/** 预约号码 */
	private String bookNumber;
	
	/** 检验流水号 */
	private String lsh;
	
	/** 预约状态 */
	private BookState bookState;
	
	/** 号牌种类ID */
	private String carTypeId;
	
	/** 号牌种类Code */
	private String carTypeCode;
	
	/** 号牌种类名称 */
	private String carTypeName;
	
	/** 号牌号码*/
	private String platNumber;
	
	/** 车架号 */
	private String frameNumber;
	
	/** 检测站编号 */
	private String stationCode;
	
	/** 检测站ID */
	private String stationId;
	
	/** 检测站名称 */
	private String stationName;
	
	/** 开始日期 */
	private Date startDate;
	
	/** 结束日期 */
	private Date endDate;
	
	/** 描述 */
	private String description;
	
	/** 预约日期/登陆日期 */
	private String bookOrLoginDate;
	
	/** 是否是异常记录，1:异常；0:正常 */
	private Integer isException;

	public String getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public BookState getBookState() {
		return bookState;
	}

	public void setBookState(BookState bookState) {
		this.bookState = bookState;
	}

	public String getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(String carTypeId) {
		this.carTypeId = carTypeId;
	}

	public String getPlatNumber() {
		return platNumber;
	}

	public void setPlatNumber(String platNumber) {
		this.platNumber = platNumber;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getCarTypeCode() {
		return carTypeCode;
	}

	public void setCarTypeCode(String carTypeCode) {
		this.carTypeCode = carTypeCode;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsException() {
		return isException;
	}

	public void setIsException(Integer isException) {
		this.isException = isException;
	}

	public String getBookOrLoginDate() {
		return bookOrLoginDate;
	}

	public void setBookOrLoginDate(String bookOrLoginDate) {
		this.bookOrLoginDate = bookOrLoginDate;
	}

	
}
