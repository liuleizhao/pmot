package com.cs.appoint.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.cs.common.entityenum.BookChannel;
import com.cs.common.entityenum.BookState;
import com.cs.common.entityenum.CheckState;
import com.cs.common.entityenum.VehicleCharacter;
import com.cs.common.utils.poi.ExcelResources;
import com.cs.mvc.dao.BaseEntity;
@XmlRootElement
public class BookInfo extends BaseEntity{

	private static final long serialVersionUID = 973989842549733116L;
	
	//车辆类型
	private String carTypeId;
	/** 号牌号码*/
    private String platNumber;
    /** 车架号*/
    private String frameNumber;
    /** 预约手机号*/
    private String mobile;
    /** 预约天*/
    private String bookDate;
    /** 预约时间*/
    private String bookTime;
    /** 检测站ID*/
    private String stationId;
    /** 预约状态*/
    private BookState bookState;
    /** 预约号*/
    private String bookNumber;
    /** 创建时间*/
    private Date createDate;
    /** 预约渠道*/
    private BookChannel bookChannel;
    /** 预约人名称*/
    private String bookerName;
    /** 证件类型ID*/
    private String idTypeId;
    /** 身份证号*/
    private String idNumber;
    /** 使用性质*/
    private String useCharater;
    /** 车辆类型（自定义）*/
    private String vehicleType;

    /** 发动机号*/
    private String engineNumber;
    /** 驱动类型*/
    private Integer driverType;
    /** 燃油类型*/
    private Integer fuelType;
    /** 车辆类型名称*/
    private String carTypeName;
    /** 检测站名称*/
    private String stationName;
    /** 证件名称*/
    private String idTypeName;
    /** 预约人ID，后台预约*/
    private String userId;
    /** 预约姓名，后台预约*/
    private String userName;
    /** 车牌性质*/
    private VehicleCharacter vehicleCharacter;
    /** 请求IP*/
    private String requestIp;

    /** 检测时间*/
    private Date checkTime;
    /** 实际检测站名称*/
    private String checkStation;
    /** 检测序列号*/
    private String checkSerialNumber;
    /** 检测标记*/
    private Integer checkOperationMark;
    
    /** 检测状态*/
    private CheckState checkState;
    
    /** 是否新车 1，旧车 0*/
	private String newflag = "0";

	/** 星级用户Id*/
	private String personId;

	/** 验证码*/
	private String verifyCode;
	/** 大客户的另一个证件号，组织机构代码*/
	private String otherIdNumber;
	
	/** 大客户的申请表ID*/
	private String compApplyFormId;
    
    @ExcelResources(title = "id", order = 1)
    public String getId(){
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    @ExcelResources(title = "carTypeId", order = 2)
    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }
    @ExcelResources(title = "platNumber", order = 3)
    public String getPlatNumber() {
        return platNumber;
    }

    public void setPlatNumber(String platNumber) {
        this.platNumber = platNumber;
    }
    @ExcelResources(title = "frameNumber", order = 4)
    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }
    @ExcelResources(title = "mobile", order = 5)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @ExcelResources(title = "bookDate", order = 6)
    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }
    @ExcelResources(title = "bookTime", order = 7)
    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }
    @ExcelResources(title = "stationId", order = 8)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @ExcelResources(title = "createDate", order = 9)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @ExcelResources(title = "idTypeId", order = 10)
    public String getIdTypeId() {
        return idTypeId;
    }

    public void setIdTypeId(String idTypeId) {
        this.idTypeId = idTypeId;
    }
    @ExcelResources(title = "idNumber", order = 11)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    @ExcelResources(title = "useCharater", order = 12)
    public String getUseCharater() {
        return useCharater;
    }

    public void setUseCharater(String useCharater) {
        this.useCharater = useCharater;
    }
    @ExcelResources(title = "vehicleType", order = 13)
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    @ExcelResources(title = "checkTime", order = 14)
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    @ExcelResources(title = "checkStation", order = 15)
    public String getCheckStation() {
        return checkStation;
    }

    public void setCheckStation(String checkStation) {
        this.checkStation = checkStation;
    }
    @ExcelResources(title = "checkSerialNumber", order = 16)
    public String getCheckSerialNumber() {
        return checkSerialNumber;
    }

    public void setCheckSerialNumber(String checkSerialNumber) {
        this.checkSerialNumber = checkSerialNumber;
    }
    @ExcelResources(title = "stationName", order = 17)
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    @ExcelResources(title = "idTypeName", order = 18)
    public String getIdTypeName() {
        return idTypeName;
    }

    public void setIdTypeName(String idTypeName) {
        this.idTypeName = idTypeName;
    }
    @ExcelResources(title = "userId", order = 19)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @ExcelResources(title = "userName", order = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @ExcelResources(title = "bookState", order = 21)
	public BookState getBookState() {
		return bookState;
	}

	public void setBookState(BookState bookState) {
		this.bookState = bookState;
	}
	 @ExcelResources(title = "bookNumber", order = 22)
	public String getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}
	 @ExcelResources(title = "bookChannel", order = 23)
	public BookChannel getBookChannel() {
		return bookChannel;
	}

	public void setBookChannel(BookChannel bookChannel) {
		this.bookChannel = bookChannel;
	}
	 @ExcelResources(title = "bookerName", order = 24)
	public String getBookerName() {
		return bookerName;
	}

	public void setBookerName(String bookerName) {
		this.bookerName = bookerName;
	}
	 @ExcelResources(title = "checkOperationMark", order = 25)
	public Integer getCheckOperationMark() {
		return checkOperationMark;
	}

	public void setCheckOperationMark(Integer checkOperationMark) {
		this.checkOperationMark = checkOperationMark;
	}
	 @ExcelResources(title = "checkState", order = 26)
	public CheckState getCheckState() {
		return checkState;
	}

	public void setCheckState(CheckState checkState) {
		this.checkState = checkState;
	}

	 @ExcelResources(title = "engineNumber", order = 27)
	public String getEngineNumber() {
		return engineNumber;
	}


	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	 @ExcelResources(title = "driverType", order =28)
	public Integer getDriverType() {
		return driverType;
	}

	public void setDriverType(Integer driverType) {
		this.driverType = driverType;
	}
	 @ExcelResources(title = "fuelType", order = 29)
	public Integer getFuelType() {
		return fuelType;
	}

	public void setFuelType(Integer fuelType) {
		this.fuelType = fuelType;
	}
	 @ExcelResources(title = "carTypeName", order = 30)
	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	 @ExcelResources(title = "vehicleCharacter", order = 31)
	public VehicleCharacter getVehicleCharacter() {
		return vehicleCharacter;
	}

	public void setVehicleCharacter(VehicleCharacter vehicleCharacter) {
		this.vehicleCharacter = vehicleCharacter;
	}
	 @ExcelResources(title = "requestIp", order = 32)
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getNewflag() {
		return newflag;
	}

	public void setNewflag(String newflag) {
		this.newflag = newflag;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getOtherIdNumber() {
		return otherIdNumber;
	}

	public void setOtherIdNumber(String otherIdNumber) {
		this.otherIdNumber = otherIdNumber;
	}

	public String getCompApplyFormId() {
		return compApplyFormId;
	}

	public void setCompApplyFormId(String compApplyFormId) {
		this.compApplyFormId = compApplyFormId;
	}

	
}