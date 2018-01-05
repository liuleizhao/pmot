package com.cs.photo.entity;

import java.util.Date;

/**
 * 服务站业务流水信息
 * @ClassName:    ServiceStationBusiness
 * @Description:  
 * @Author        succ
 * @date          2017-9-20  下午5:35:05
 */
public class ServiceStationBusiness {
	/** 流水号 */
	private String serialNum;
	/** 号牌号码 */
	private String plateNumber;
	/** 号牌种类 */
	private String plateType;
	/** 审核结束日期 */
	private Date auditEndDate;
	
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
	public Date getAuditEndDate() {
		return auditEndDate;
	}
	public void setAuditEndDate(Date auditEndDate) {
		this.auditEndDate = auditEndDate;
	}
	
}
