package com.cs.webservice.dto;

import java.util.List;

/**
 * 同步数据实体
 * @ClassName:    SynchronizeEntity
 * @Description:  
 * @Author        succ
 * @date          2017-10-27  下午3:22:27
 */
public class SynchronizeEntity {
	private String tableName;
	private List<String> columns;
	private List<String> values;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
}
