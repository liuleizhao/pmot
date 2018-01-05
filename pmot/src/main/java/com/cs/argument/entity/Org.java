package com.cs.argument.entity;

import com.cs.mvc.dao.BaseEntity;

public class Org extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8338702293303798100L;

	/** 参数值 */
    private String code;

    /** 机构名称 */
    private String name;

    /**
	 * 14-05-05 地理位置坐标x,y
	 */
    private String pointx;

    private String pointy;

    /**
	 * 14-05-22 是否需要IP限制
	 */
    private String isIpLock;

    /**
	 * 14-05-23 机构绑定Ip地址
	 */
    private String orgIp;

    /**
	 * 地址
	 */
    private String address;

    /**
	 * 可以办理的业务类型 （机动车为1、驾驶证为2 、必须有一个，可以双选，逗号分隔）
	 */
    private String businessType;

    /**
	 * 所属区
	 */
    private String district;

    /**
	 * 图片（多个图片以逗号分隔）
	 */
    private String image;

    /**
	 * 电话
	 */
    private String telephone;

    /**
	 * 非工作日业务办理时间（格式：09:00-11:30，14:00-18:00 逗号分隔上午、下午）
	 */
    private String transactWeekendTime;

    /**
	 * 工作日业务办理时间（格式：09:00-11:30，14:00-18:00 逗号分隔上午、下午）
	 */
    private String transactWorkdayTime;

    /**
	 * 1：开启 0：关闭
	 */
    private Integer status = 1; // 默认开启

    /** 地址 */
    private String description;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPointx() {
        return pointx;
    }

    public void setPointx(String pointx) {
        this.pointx = pointx;
    }

    public String getPointy() {
        return pointy;
    }

    public void setPointy(String pointy) {
        this.pointy = pointy;
    }

    public String getIsIpLock() {
        return isIpLock;
    }

    public void setIsIpLock(String isIpLock) {
        this.isIpLock = isIpLock;
    }

    public String getOrgIp() {
        return orgIp;
    }

    public void setOrgIp(String orgIp) {
        this.orgIp = orgIp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTransactWeekendTime() {
        return transactWeekendTime;
    }

    public void setTransactWeekendTime(String transactWeekendTime) {
        this.transactWeekendTime = transactWeekendTime;
    }

    public String getTransactWorkdayTime() {
        return transactWorkdayTime;
    }

    public void setTransactWorkdayTime(String transactWorkdayTime) {
        this.transactWorkdayTime = transactWorkdayTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}