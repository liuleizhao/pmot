package com.cs.webservice.entity;

import java.util.Date;

import com.cs.mvc.dao.BaseEntity;

/**
 * 接口调用日志实体
 * @ClassName:    InterfaceLog
 * @Description:  
 * @Author        succ
 * @date          2017-10-31  上午11:29:47
 */
public class InterfaceLog extends BaseEntity{
  
	private static final long serialVersionUID = 6371123209081339174L;

	/** 接口ID */
    private String jkid;

    /** 调用者IP地址 */
    private String ip;

    /** 调用时间 */
    private Date requestDate;

    /** 请求XML */
    private String requestXml;

    /** 响应时间 */
    private Date responseDate;

    /** 响应报文 */
    private String responseXml;

    /** 运行时间 */
    private Integer runTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestXml() {
        return requestXml;
    }

    public void setRequestXml(String requestXml) {
        this.requestXml = requestXml;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public String getResponseXml() {
        return responseXml;
    }

    public void setResponseXml(String responseXml) {
        this.responseXml = responseXml;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }
}