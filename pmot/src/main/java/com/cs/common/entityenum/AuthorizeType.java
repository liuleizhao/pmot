package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

/**
 * @描述：授权类型
 * @版本：1.0
 */
public enum AuthorizeType implements Identifiable<Integer>{
	/** 正常授权 */
	NORMAL(0, "正常授权"),
	/** 临时授权 */
	TEMP(1, "临时授权");
	
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private AuthorizeType(int index, String description) {
		this.index = index;
		this.description = description;
	}
	@Override
	public Integer getId() {
		return this.index;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
 
	public static List<AuthorizeType> getAll() {
		return Arrays.asList(AuthorizeType.class.getEnumConstants());
	}

	public static AuthorizeType findByIndex(Integer index) {
		return EnumUtils.getEnum(AuthorizeType.class, index);
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
