package com.cs.appoint.service;

import java.util.List;
import java.util.Map;

import com.cs.appoint.entity.SyncTask;
import com.cs.mvc.service.BaseService;

public interface SyncTaskService extends BaseService<SyncTask, String>{
	/**
	 * 根据表名查询未同步的数据
	 * @param tableName
	 * @return
	 * @Author        succ
	 * @date 2017-11-8 下午12:20:31
	 */
	public List<Map<String,Object>> findNotSyncData(String tableName);
	
	/**
	 * 开始同步，更新开始时间
	 * @param list
	 * @Author        succ
	 * @date 2017-11-8 下午12:23:58
	 */
	public void startSyncTask(List<SyncTask> list);
	
	/**
	 * 完成同步，更新完成时间
	 * @param list
	 * @Author        succ
	 * @date 2017-11-8 下午4:20:08
	 */
	public void finishSyncTask(List<String> ids);
}
