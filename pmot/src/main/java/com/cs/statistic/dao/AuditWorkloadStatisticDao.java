package com.cs.statistic.dao;

import java.util.List;

import com.cs.statistic.dto.AuditWorkloadDto;
import com.cs.statistic.entity.AuditWorkloadStatistic;

public interface AuditWorkloadStatisticDao {
	public List<AuditWorkloadStatistic> list(AuditWorkloadDto auditWorkloadDto); 
}
