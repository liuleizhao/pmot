package com.cs.common.utils.webservice;

import java.util.Arrays;
import java.util.List;

/**
 * WebService接口返回的Code值
 * @ClassName:    ResultCode
 * @Description:  
 * @Author        succ
 * @date          2017-11-1  下午2:26:35
 */
public enum ResultCode {

	SUCCESS("1", "请求成功"),
	FAIL("-1","请求失败"),
	SYSTEM_ERROR("$E","系统错误");
	
	/** 顺序 */
	private String code;

	/** 描述 */
	private String description;

	private ResultCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

	public static List<ResultCode> getAll() {
		return Arrays.asList(ResultCode.class.getEnumConstants());
	}

	public static ResultCode findByCode(String code) {
		ResultCode[] results = ResultCode.class.getEnumConstants();
		for (ResultCode result : results) {
			if (result.getCode().equals(code)) {
				return result;
			}
		}
		throw new AssertionError("不能够映射:" + code + "到枚举"
				+ ResultCode.class.getSimpleName());
	}

	public String getValue() {
		return this.name();
	}

	public String toString() {
		return this.name();
	}
}
