package com.cs.argument.entity;


import com.cs.mvc.dao.BaseEntity;

public class District extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8164073515802221064L;

	/** 代码*/
	private String code;

    private String descName;

    /** 区县名称*/
    private String name;

    /** 显示顺序*/
    private Integer orderNum;

    /** X坐标*/
    private Double pointX;

    /** Y坐标*/
    private Double pointY;

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

    public String getDescName() {
        return descName;
    }

    public void setDescName(String descName) {
        this.descName = descName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPointX() {
        return pointX;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    public Double getPointY() {
        return pointY;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
    
}