package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum BreakPromiseRecordState  implements Identifiable<Integer>{
	/** 失效 */
	SX(0, "失效"),

	/** 有效 */
	YX(1, "有效");
	
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private BreakPromiseRecordState(int index, String description) {
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
	
	public static List<BreakPromiseRecordState> getAll() {
		return Arrays.asList(BreakPromiseRecordState.class.getEnumConstants());
	}

	public static BreakPromiseRecordState findByIndex(Integer index) {
		return EnumUtils.getEnum(BreakPromiseRecordState.class, index);
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
