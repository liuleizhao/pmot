package com.cs.appoint.dao;

import java.util.List;
import java.util.Map;

import com.cs.appoint.entity.SyncTask;
import com.cs.mvc.dao.BaseDao;

public interface SyncTaskDao extends BaseDao<SyncTask, String>{

	public List<Map<String,Object>> findNotSyncData(String tableName);
	
	
	public List<SyncTask> findSyncTaskByState(Integer state);
	
	/**
	 * 
	 * @param list 开始的任务
	 */
	public void startSyncTask(List<SyncTask> list);
	/**
	 * 完成任务
	 * @param ids
	 * @Author        succ
	 * @date 2017-11-8 下午4:23:35
	 */
	public void finishSyncTask(List<String> ids);
}