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
public enum Sex implements Identifiable<Integer> {
	/** 男 */
	MAN   (10, "男"),
	/** 女 */
	WOMEN (11, "女");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	private Sex(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<Sex> getAll() {
		return Arrays.asList(Sex.class.getEnumConstants());
	}

	public static Sex findByIndex(Integer index) {
		return EnumUtils.getEnum(Sex.class, index);
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
