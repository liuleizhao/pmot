package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

/**
 * 网上预约状态枚举
 * 
 * @author 肖佳
 * 
 */
public enum InterfaceControlState implements Identifiable<Integer> {

	/** 停用 */
	TY(0, "停用"),

	/** 使用中 */
	SYZ(1, "使用中");
	
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private InterfaceControlState(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	@Override
	public Integer getId() {
		return this.index;
	}
	
	public static List<InterfaceControlState> getAll() {
		return Arrays.asList(InterfaceControlState.class.getEnumConstants());
	}

	public static InterfaceControlState findByIndex(Integer index) {
		return EnumUtils.getEnum(InterfaceControlState.class, index);
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

}
