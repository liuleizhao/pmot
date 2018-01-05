package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

/**
 * 提交方式
 * 
 * @author vincent
 * 
 */
public enum MethodType implements Identifiable<Integer> {

	GET(1, "GET"), POST(2, "POST"),

	PUT(3, "PUT"), DELETE(4, "DELETE")

	;

	private int type;
	private String description;

	private MethodType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	@Override
	public Integer getId() {
		return this.type;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public static List<MethodType> getAll() {
		return Arrays.asList(MethodType.class.getEnumConstants());
	}

	public static MethodType findByIndex(Integer index) {
		return EnumUtils.getEnum(MethodType.class, index);
	}

	public String getValue() {
		return this.name();
	}

	public String toString() {
		return this.name();
	}

}
