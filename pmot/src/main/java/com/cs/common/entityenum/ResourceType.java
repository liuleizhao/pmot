package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;


/**
 * @描述：资源类型枚举
 * @版本：1.0
 */
public enum ResourceType implements Identifiable<Integer> {
    /** 模块 */
    MODULE   (1, "模块"),
	/** 菜单 */
	MENU     (2, "菜单"),
	/** 功能类型 */
	FUNCTION (3, "功能类型"),
	/** 引用菜单 */
	REFERENCE(4, "引用菜单");
    
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private ResourceType(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<ResourceType> getAll() {
		return Arrays.asList(ResourceType.class.getEnumConstants());
	}

	public static ResourceType findByIndex(Integer index) {
		return EnumUtils.getEnum(ResourceType.class, index);
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
