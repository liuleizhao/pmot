package com.cs.argument.entity;

import java.util.Date;

import com.cs.common.entityenum.StationStatus;
import com.cs.mvc.dao.BaseEntity;

public class Station extends BaseEntity {

	private static final long serialVersionUID = 8147882985414154937L;
	/** 检测站编号 */
	private String code;

	/** 检测站序号 （排序用） */
	private Integer orderNum;

	/** 检测站名称 */
	private String name;

	/** 接口序列号 */
	private String serialNumber;

	/** 检测站地址 */
	private String address;

	/** 手机号码 */
	private String mobile;

	/** 固定电话 */
	private String phone;

	/** email */
	private String email;

	/** 创建日期 */
	private Date createDate = new Date();

	/** 地理位置X */
	private Double pointX;

	/** 地理位置 Y */
	private Double pointY;

	/** 地区ID */
	private String districtId;

	/** 所属组织 */
	private String orgId;

	/** 状态 */
	private StationStatus stationState = StationStatus.YES;

	/** 绿标 */
	// GREEN_MARK
	private Integer greenMark;
	
	/** 可办理车辆性质列表，多个使用“,”隔开 */
    private String vehicleCharacters;

    private String driverTypes;

    private String fuelTypes;

    private String vehicleTypes;

    private String useCharaters;

    private String carTypes;
    
    /** 检测站名称简称  */
    private String viewName;

    /** 检测站线数  */
    private Integer lineNumber;

    /** 绿色通道->检测站一天最大预约量  */
    private Integer maxNumber;
    
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public StationStatus getStationState() {
		return stationState;
	}

	public void setStationState(StationStatus stationState) {
		this.stationState = stationState;
	}

	public Integer getGreenMark() {
		return greenMark;
	}

	public void setGreenMark(Integer greenMark) {
		this.greenMark = greenMark;
	}

	public String getVehicleCharacters() {
		return vehicleCharacters;
	}

	public void setVehicleCharacters(String vehicleCharacters) {
		this.vehicleCharacters = vehicleCharacters;
	}

	public String getDriverTypes() {
		return driverTypes;
	}

	public void setDriverTypes(String driverTypes) {
		this.driverTypes = driverTypes;
	}

	public String getFuelTypes() {
		return fuelTypes;
	}

	public void setFuelTypes(String fuelTypes) {
		this.fuelTypes = fuelTypes;
	}

	public String getVehicleTypes() {
		return vehicleTypes;
	}

	public void setVehicleTypes(String vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
	}

	public String getUseCharaters() {
		return useCharaters;
	}

	public void setUseCharaters(String useCharaters) {
		this.useCharaters = useCharaters;
	}

	public String getCarTypes() {
		return carTypes;
	}

	public void setCarTypes(String carTypes) {
		this.carTypes = carTypes;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}
}