package com.cs.appoint.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cs.appoint.dao.CompleteDao;
import com.cs.appoint.entity.Complete;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseService;

public interface CompleteService extends BaseService<Complete, String> {

	public List<Complete> findComplete(String stationId, Date startDate, Date endDate) throws Exception;
}
