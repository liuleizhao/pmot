package com.cs.photo.dto;

import com.cs.photo.entity.PhotoInfo;

public class PhotoInfoDto extends PhotoInfo {
	/** 管理部门 */
	private String glbm;
	/** 业务办理单位类型 */
	private Integer orgType;
	/** 号牌号码 */
	private String plateNumber;
	/** 号牌种类 */
	private String plateType;
	
	
	public String getGlbm() {
		return glbm;
	}
	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
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
	
	
}
