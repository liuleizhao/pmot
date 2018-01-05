package com.cs.appoint.entity;

import java.util.Date;

import com.cs.common.utils.poi.ExcelResources;
import com.cs.mvc.dao.BaseEntity;
/**
 * 网上预约取消预约实体
 * @author xj
 *
 */
public class Cancel extends BaseEntity{
	
	private static final long serialVersionUID = -1142064387882422954L;
	
	private String id;
	/** 预约号 */
	private String bookNumber;
	
	/** 电话号码*/
    private String mobile;
    
    /** 证件号码*/
    private String idNumber;
    
    /** 号牌号码*/
    private String platNumber;
    
    /** IP地址*/
    private String ip;
    
    /** 证件类型 */
    private String idType;
    
    /** 创建时间 */
    private Date createDate = new Date();

    @ExcelResources(title = "id", order = 1)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @ExcelResources(title = "bookNumber", order = 2)
    public String getBookNumber() {
        return bookNumber;
    }
    
    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }
    @ExcelResources(title = "createDate", order = 3)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @ExcelResources(title = "mobile", order = 4)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @ExcelResources(title = "idNumber", order = 5)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    @ExcelResources(title = "platNumber", order = 6)
    public String getPlatNumber() {
        return platNumber;
    }

    public void setPlatNumber(String platNumber) {
        this.platNumber = platNumber;
    }
    @ExcelResources(title = "ip", order = 7)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    @ExcelResources(title = "idType", order = 8)
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }
}