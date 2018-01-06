package com.cs.appoint.entity;

import java.util.Date;

import com.cs.mvc.dao.BaseEntity;

/**
 * 车牌，车架白名单
 * @ClassName:    PlatNumberWhiteList
 * @Description:  
 * @Author        xj
 * @date          2018-01-06 
 */
public class PlatNumberWhiteList extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5949533621811974856L;
	
	/** 号牌号码*/
	private String platNumber;
	
	/** 车架号*/
    private String frameNumber;
    
    /** 号牌种类*/
    private String carTypeId;
    
    /** 检测站ID*/
    private String stationId;
    
    /** 创建时间*/
    private Date createDate;
    
    /** 号牌种类名称*/
    private String carTypeName;
    
    /** 检测站名称*/
    private String stationName;
    
    /** 是否新车 1，旧车 0*/
    private String newFlag = "0";
    
    /** 号牌种类编号*/
    private String carTypeCode;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getCarTypeCode() {
		return carTypeCode;
	}

	public void setCarTypeCode(String carTypeCode) {
		this.carTypeCode = carTypeCode;
	}
    
}