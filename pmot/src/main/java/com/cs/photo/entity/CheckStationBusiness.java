package com.cs.photo.entity;

import java.util.Date;

/**
 * 检测站业务流水信息
 * @ClassName:    CheckStationBusiness
 * @Description:  
 * @Author        succ
 * @date          2017-9-20  下午5:34:48
 */
public class CheckStationBusiness {
	/** 流水号 */
	private String serialNum;
	/** 号牌号码 */
	private String plateNumber;
	/** 号牌种类 */
	private String plateType;
	/** 创建日期 */
	private Date createDate;
	
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getPlateType() {
		return plateType;
	}
	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
