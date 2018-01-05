package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;
public enum IsOpenType implements Identifiable<Integer> {
	
	/** 开通业务 */
	YES  (1, "是"),
	/** 不开通业务 */
	NO   (0, "否");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	@Override
	public Integer getId() {
		return this.index;
	}
	
	private IsOpenType(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<IsOpenType> getAll() {
		return Arrays.asList(IsOpenType.class.getEnumConstants());
	}

	public static IsOpenType findByIndex(Integer index) {
		return EnumUtils.getEnum(IsOpenType.class, index);
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
