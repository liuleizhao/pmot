package com.cs.system.service;

import com.cs.common.entityenum.GlobalConfigKey;
import com.cs.mvc.service.BaseService;
import com.cs.system.entity.GlobalConfig;
/**
 * 全局配置Service
 * @ClassName:    GlobalConfigService
 * @Description:  
 * @Author        succ
 * @date          2017-11-1  下午5:31:50
 */
public interface GlobalConfigService extends BaseService<GlobalConfig, String> {
	public GlobalConfig findByConfigKey(GlobalConfigKey key) throws Exception;
	
	public GlobalConfig findByDataKey(String dataKey) throws Exception;
}
