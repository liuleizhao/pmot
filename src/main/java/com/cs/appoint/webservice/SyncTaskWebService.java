package com.cs.appoint.webservice;

/**
 * 同步信息对外接口
 * @ClassName:    SyncTaskWebService
 * @Description:  
 * @Author        succ
 * @date          2017-11-8  下午12:13:27
 */
public interface SyncTaskWebService {
	/**
	 * 查询未同步的数据
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-9 下午3:23:38
	 */
	public String findNotSyncData() throws Exception;
	
	/**
	 * 完成同步回调方法
	 * @param ids 完成的任务ID，逗号分隔
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-9 下午3:23:47
	 */
	public String finishSyncTask(String ids) throws Exception;
	
	/**
	 * 导入数据
	 * @param xmlData
	 * @return
	 * @throws Exception
	 * @Author        succ
	 * @date 2017-11-9 下午3:23:31
	 */
	public String importData(String xmlData) throws Exception;
}
