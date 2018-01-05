package com.cs.appoint.entity;

import java.util.Date;

import com.cs.mvc.dao.BaseEntity;

/**
 * 预约兼容实体（按检测站区分）
 * @ClassName:    BookInfoCompatible
 * @Description:  
 * @Author        succ
 * @date          2017-11-10  下午5:33:53
 */
public class BookInfoCompatible extends BaseEntity {

	private static final long serialVersionUID = -2132402557540863463L;

	/** 检测站ID */
    private String stationId;

    /** 兼容时长（单位为分钟） */
    private Integer compatibleValue;

    /** 生效开始日期 */
    private Date startDate;

    /** 生效结束日期 */
    private Date endDate;

    /** 创建日期 */
    private Date createDate;
    
    /** 检测站名称 */
    private String stationName;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Integer getCompatibleValue() {
        return compatibleValue;
    }

    public void setCompatibleValue(Integer compatibleValue) {
        this.compatibleValue = compatibleValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
    
}