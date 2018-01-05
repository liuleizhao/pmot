package com.cs.data.service;

import java.util.List;

import com.cs.data.entity.Export;
import com.cs.mvc.service.BaseService;

public interface ConfigureService extends BaseService<Export, String>{
	
	public boolean checkTableName(String tableName) throws Exception;
	
	public List<String> findTableName();
	
	public List<String> findTableCol(String tableName);
	
	public List<Export> findEnable() throws Exception;
	
}
