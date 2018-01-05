package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum Visible implements Identifiable<Integer>{
	/** 男 */
	USE (0, "启用"),
	/** 女 */
	DISABLE (1, "禁用");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	private Visible(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<Visible> getAll() {
		return Arrays.asList(Visible.class.getEnumConstants());
	}

	public static Visible findByIndex(Integer index) {
		return EnumUtils.getEnum(Visible.class, index);
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
