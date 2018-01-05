package com.cs.statistic.dto;

import java.util.Date;

import com.cs.statistic.entity.AuditWorkloadStatistic;

public class AuditWorkloadDto extends AuditWorkloadStatistic {
	/** 审核日期 */
	private Date auditDate;

	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
}
