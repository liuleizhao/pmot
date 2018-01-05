package com.cs.appoint.entity;

import java.util.Date;

import com.cs.mvc.dao.BaseEntity;

/**
 * 流水信息审核日志
 * @ClassName 	VehIsFlowInspectionLog
 * @Description
 * @Author 		suchaochen
 * @Date 		2017-12-26	 下午8:59:06
 */
public class VehIsFlowInspectionLog extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/** 检测站编码 */
	private String stationCode;
	/** 流水号 */
	private String lsh;
	/** 发证机关 */
	private String fzjg;
	/** 号牌号码 */
	private String platNumber;
	/** 车架号 */
	private String frameNumber;
	/** 号牌种类 */
	private String carTypeCode;
	/** 预约号 */
	private String bookNumber;
	/** 预约时间段 */
	private String bookTime;
	/** 审核结果代码 1:验证通过;2:流水号为空;3:流水信息不存在; 4:没有预约; 5:不在预约时间段办理*/
	private int resultCode;
	/** 创建时间 */
	private Date createDate;
	
	
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
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
	public String getCarTypeCode() {
		return carTypeCode;
	}
	public void setCarTypeCode(String carTypeCode) {
		this.carTypeCode = carTypeCode;
	}
	public String getBookNumber() {
		return bookNumber;
	}
	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getBookTime() {
		return bookTime;
	}
	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	
}
