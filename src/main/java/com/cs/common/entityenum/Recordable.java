package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;
/**
 * @描述：性别
 * @作者：肖佳
 * @开发日期：2011-6-21
 * @版权：永泰软件有限公司
 * @版本：1.0
 */
public enum Recordable implements Identifiable<Integer> {
	/** 需记录 */
	YES   (1, "需记录"),
	/** 无需记录 */
	NO (0, "无需记录");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	private Recordable(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<Recordable> getAll() {
		return Arrays.asList(Recordable.class.getEnumConstants());
	}

	public static Recordable findByIndex(Integer index) {
		return EnumUtils.getEnum(Recordable.class, index);
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
