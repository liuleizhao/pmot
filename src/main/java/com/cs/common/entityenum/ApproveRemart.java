package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum ApproveRemart implements Identifiable<Integer>{
	/** 预约中 */
	AGREE(1, "同意"),

	/** 预约完成 */
	OPPOSE(2, "反对"),

	;
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private ApproveRemart(int index, String description) {
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
	
	public static List<ApproveRemart> getAll() {
		return Arrays.asList(ApproveRemart.class.getEnumConstants());
	}

	public static ApproveRemart findByIndex(Integer index) {
		return EnumUtils.getEnum(ApproveRemart.class, index);
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
