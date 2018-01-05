package com.cs.appoint.entity;

import java.util.Date;

import com.cs.common.utils.poi.ExcelResources;
import com.cs.mvc.dao.BaseEntity;

public class Complete extends BaseEntity{
   
	
	private String id;

    private String bookNumber;

    private String platNumber;

    private String mobile;

    private String idType;

    private String idNumber;

    private Date createDate;

    public Complete(){}
	public Complete(BookInfo bookInfo){
		this.setBookNumber(bookInfo.getBookNumber());
		this.setCreateDate(new Date());
		this.setIdNumber(bookInfo.getIdNumber());
		this.setIdType(bookInfo.getIdTypeId());
		this.setMobile(bookInfo.getMobile());
		this.setPlatNumber(bookInfo.getPlatNumber());
	}
    
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

    @ExcelResources(title = "platNumber", order = 3)
    public String getPlatNumber() {
        return platNumber;
    }

    public void setPlatNumber(String platNumber) {
        this.platNumber = platNumber;
    }

    @ExcelResources(title = "mobile", order = 4)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ExcelResources(title = "idType", order = 5)
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @ExcelResources(title = "idNumber", order = 6)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @ExcelResources(title = "createDate", order = 7)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}