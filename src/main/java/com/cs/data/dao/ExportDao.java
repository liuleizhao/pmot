package com.cs.data.dao;

import java.util.List;

import com.cs.data.entity.Export;
import com.cs.mvc.dao.BaseDao;

public interface ExportDao extends BaseDao<Export, String>{
	List<String> findTableName();
	
	
	List<String> findTableCol(String tableName);
	
}