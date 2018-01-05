package com.cs.appoint.vo;


public class BookInfoVO {
	
    /** 预约号*/
    private String bookNumber;

	/** 姓名 */
	private String name;
    
	/** 证件种类id */
	private String idTypeId;
	
	/** 证件种类名称 */
	private String idTypeName;
	
	/** 证件号码 */
	private String idNumber;
	
	/** 号牌号码*/
    private String platNumber;
    
    /** 号牌号码*/
    private String frameNumber;
    
	/** 车辆类型id*/
	private String carTypeId;
	/** 车辆类型*/
	private String carTypeName;
	
    /** 预约手机号*/
    private String mobile;
    
    /** 预约天*/
    private String bookDate;
    
    /** 预约时间*/
    private String bookTime;
    
    /** 监测站ID*/
    private String stationId;
 
    /** 监测站名称*/
    private String stationName;
	
	/**预约状态*/
	private Integer bookState;

	public String getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdTypeId() {
		return idTypeId;
	}

	public void setIdTypeId(String idTypeId) {
		this.idTypeId = idTypeId;
	}

	public String getIdTypeName() {
		return idTypeName;
	}

	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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

	public String getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(String carTypeId) {
		this.carTypeId = carTypeId;
	}

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getBookState() {
		return bookState;
	}

	public void setBookState(Integer bookState) {
		this.bookState = bookState;
	}
	
}
