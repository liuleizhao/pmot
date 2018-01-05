package com.cs.photo.dao;

import com.cs.photo.entity.ServiceStationBusiness;

public interface ServiceStationBusinessDao {
	/**
	 * 根据条件查询最新的记录
	 * @param checkStationBusiness
	 * @return
	 * @Author        succ
	 * @date 2017-9-20 下午5:51:16
	 */
	public ServiceStationBusiness findLatestByCondition(ServiceStationBusiness serviceStationBusiness);
}
