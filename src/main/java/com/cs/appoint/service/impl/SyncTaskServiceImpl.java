package com.cs.appoint.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.cs.appoint.dao.SyncTaskDao;
import com.cs.appoint.entity.SyncTask;
import com.cs.appoint.service.SyncTaskService;
import com.cs.mvc.dao.BaseDao;
import com.cs.mvc.service.BaseServiceSupport;

@Service
@Transactional
public class SyncTaskServiceImpl extends BaseServiceSupport<SyncTask, String> implements SyncTaskService {
	
	@Autowired
	private SyncTaskDao syncTaskDao;
	
	@Override
	protected BaseDao<SyncTask, String> getBaseDao() throws Exception {
		return syncTaskDao;
	}
	
	@Override
	public List<Map<String, Object>> findNotSyncData(String tableName) {
		return syncTaskDao.findNotSyncData(tableName);
	}

	@Override
	public void startSyncTask(List<SyncTask> list) {
		syncTaskDao.startSyncTask(list);
		
	}

	@Override
	public void finishSyncTask(List<String> ids) {
		syncTaskDao.finishSyncTask(ids);
	}

}
