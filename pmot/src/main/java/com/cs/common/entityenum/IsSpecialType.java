package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

public enum IsSpecialType implements Identifiable<Integer> {
	
	/** 特殊业务 */
	YES  (1, "特殊业务"),
	/** 普通业务 */
	NO   (0, "普通业务");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	@Override
	public Integer getId() {
		return this.index;
	}
	
	private IsSpecialType(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<IsSpecialType> getAll() {
		return Arrays.asList(IsSpecialType.class.getEnumConstants());
	}

	public static IsSpecialType findByIndex(Integer index) {
		return EnumUtils.getEnum(IsSpecialType.class, index);
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
