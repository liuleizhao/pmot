package com.cs.statistic.service;

import java.util.List;

import com.cs.statistic.dto.AuditWorkloadDto;
import com.cs.statistic.entity.AuditWorkloadStatistic;

/**
 * 审核工作量Service
 * @ClassName:    AuditWorkloadService
 * @Description:  
 * @Author        succ
 * @date          2017-8-28  上午11:31:32
 */
public interface AuditWorkloadService {
	public List<AuditWorkloadStatistic> list(AuditWorkloadDto auditWorkloadDto);
	
	public String aMethod(String argsXML);
}
