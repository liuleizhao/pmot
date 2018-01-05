package com.cs.appoint.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cs.appoint.entity.Complete;
import com.cs.mvc.dao.BaseDao;

public interface CompleteDao extends BaseDao<Complete, String>{
	
	public List<Complete> findComplete(@Param("stationId")String stationId,@Param("startDate")Date startDate,@Param("endDate")Date endDate);

}