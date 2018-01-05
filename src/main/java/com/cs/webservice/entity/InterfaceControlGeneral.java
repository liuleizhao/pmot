package com.cs.webservice.entity;

import com.cs.common.entityenum.InterfaceControlState;
import com.cs.mvc.dao.BaseEntity;

public class InterfaceControlGeneral extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String stationName;

    private String stationId;

    private String serialNumber;

    private String ips;

    private InterfaceControlState state;


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

	public InterfaceControlState getState() {
		return state;
	}

	public void setState(InterfaceControlState state) {
		this.state = state;
	}
   
}