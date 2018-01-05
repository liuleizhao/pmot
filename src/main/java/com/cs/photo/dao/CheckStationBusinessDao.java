package com.cs.photo.dao;

import com.cs.photo.entity.CheckStationBusiness;

public interface CheckStationBusinessDao {
	/**
	 * 根据条件查询最新的记录
	 * @param checkStationBusiness
	 * @return
	 * @Author        succ
	 * @date 2017-9-20 下午5:51:16
	 */
	public CheckStationBusiness findLatestByCondition(CheckStationBusiness checkStationBusiness);
}
