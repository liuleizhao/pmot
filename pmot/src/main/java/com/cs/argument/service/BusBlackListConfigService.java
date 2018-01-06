package com.cs.argument.service;

import java.util.List;

import com.cs.argument.entity.BusBlackListConfig;
import com.cs.common.entityenum.BookOperation;
import com.cs.mvc.service.BaseService;

public interface BusBlackListConfigService  extends  BaseService<BusBlackListConfig, String>{

	/**
	 * 通过操作类型查询失约配置
	 * @param cancel
	 * @return
	 */
	List<BusBlackListConfig> findByBookOperation(BookOperation cancel) throws Exception ;

}
