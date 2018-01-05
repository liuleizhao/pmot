package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

/**
 * 用户状态
 * 
 * @author vincent
 * 
 */
public enum UserStatus implements Identifiable<Integer> {

	ENABLE(1, "使用中"), DISABLE(2, "停用");

	private int type;
	private String description;

	private UserStatus(int type, String description) {
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

	public static List<UserStatus> getAll() {
		return Arrays.asList(UserStatus.class.getEnumConstants());
	}

	public static UserStatus findByIndex(Integer index) {
		return EnumUtils.getEnum(UserStatus.class, index);
	}

	public String getValue() {
		return this.name();
	}

	public String toString() {
		return this.name();
	}

}
