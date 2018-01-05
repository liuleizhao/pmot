package com.cs.webservice.entity;

import java.util.Date;

import com.cs.common.entityenum.InterfaceControlState;
import com.cs.mvc.dao.BaseEntity;

public class InterfaceControlDetail extends BaseEntity{
 
	private static final long serialVersionUID = 1L;

	private String generalId;

    private String interfaceId;

    private Long times;

    private Date createDate;
    
    private InterfaceControlState state;

    public String getGeneralId() {
        return generalId;
    }

    public void setGeneralId(String generalId) {
        this.generalId = generalId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public InterfaceControlState getState() {
		return state;
	}

	public void setState(InterfaceControlState state) {
		this.state = state;
	}
    
}