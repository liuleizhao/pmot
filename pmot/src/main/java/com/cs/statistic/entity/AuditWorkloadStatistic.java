package com.cs.statistic.entity;

/**
 * 审核工作量统计
 * @ClassName:    AuditWorkloadStatistic
 * @Description:  
 * @Author        succ
 * @date          2017-8-28  上午11:07:41
 */
public class AuditWorkloadStatistic {
	/** 审核人ID */
	private String auditorId;
	/** 审核工作量 */
	private Integer auditCount;
	/** 审核类型 1:通过 2:退办 */
	private Integer auditType;
	
	public String getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}
	public Integer getAuditCount() {
		return auditCount;
	}
	public void setAuditCount(Integer auditCount) {
		this.auditCount = auditCount;
	}
	public Integer getAuditType() {
		return auditType;
	}
	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}
	@Override
	public String toString() {
		return "AuditWorkloadStatistic [auditorId=" + auditorId
				+ ", auditCount=" + auditCount + ", auditType=" + auditType
				+ "]";
	}
	
}
