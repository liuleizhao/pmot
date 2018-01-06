package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum InterfaceInvokeType implements Identifiable<Integer> {
	
	STATION (1, "第三方调用"),
	TIMER (2, "定时器调用"),
	TEST  (3,"测试调用");

	
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	private InterfaceInvokeType(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<InterfaceInvokeType> getAll() {
		return Arrays.asList(InterfaceInvokeType.class.getEnumConstants());
	}

	public static InterfaceInvokeType findByIndex(Integer index) {
		return EnumUtils.getEnum(InterfaceInvokeType.class, index);
	}
	
	/**
	 * 用于struts2标签。如s:select标签的自动回显
	 * @return
	 */
	public String getValue(){
		return this.name();
	}

	public String toString() {
		return this.name();
	}

	@Override
	public Integer getId() {
		return this.index;
	}
}
