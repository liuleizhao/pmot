package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

/**
 * 黑名单状态枚举
 * 
 * @author 肖佳
 * 
 */
public enum BlacklistState implements Identifiable<Integer>  {

	/** 撤销 */
	CX(1, "撤销成功"),

	/** 生效中 */
	SX(2, "生效中");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;



	private BlacklistState(int index, String description) {
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

	public static List<BlacklistState> getAll() {
		return Arrays.asList(BlacklistState.class.getEnumConstants());
	}

	public static BlacklistState findByIndex(Integer index) {
		return EnumUtils.getEnum(BlacklistState.class, index);
	}

	/**
	 * 用于struts2标签。如s:select标签的自动回显
	 * 
	 * @return
	 */
	public String getValue() {
		return this.name();
	}

	public String toString() {
		return this.name();
	}

}
