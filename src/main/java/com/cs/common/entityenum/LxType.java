package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

public enum LxType implements Identifiable<Integer> {
	
	/** 驾驶证 */
	JSZ  (1, "驾驶证业务"),
	/** 机动车 */
	JDC   (2, "机动车业务"),
	
	QT (3,"其他业务");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private LxType(int index, String description) {
		this.index = index;
		this.description = description;
	}

	@Override
	public Integer getId() {
		return this.index;
	}
	
	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<LxType> getAll() {
		return Arrays.asList(LxType.class.getEnumConstants());
	}

	public static LxType findByIndex(Integer index) {
		return EnumUtils.getEnum(LxType.class, index);
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
