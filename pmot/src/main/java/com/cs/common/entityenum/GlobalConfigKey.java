package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.mvc.mybatis.enumhandler.Identifiable;

/**
 * 全局配置Key
 * @ClassName:    GlobalConfigKey
 * @Description:  
 * @Author        succ
 * @date          2017-11-1  下午5:45:32
 */
public enum GlobalConfigKey implements Identifiable<String> {
	FREE_VERIFY("FREE_VERIFY","预约验证配置"),BOOK_COMPATIBLE("BOOK_COMPATIBLE","预约时间兼容配置");
	
	private String id;
	/** 描述 */
	private String description;
	
	private GlobalConfigKey(String id,String description){
		this.id = id;
		this.description = description;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public static List<GlobalConfigKey> getAll() {
		return Arrays.asList(GlobalConfigKey.class.getEnumConstants());
	}
}
