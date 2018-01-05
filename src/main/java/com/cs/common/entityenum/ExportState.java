package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;
import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;

/**
 * 导出配置状态
 * @author vincent
 * 
 */
public enum ExportState implements Identifiable<Integer> {

	ENABLE(1, "使用中"), DISABLE(2, "停用");

	private int index;
	private String description;

	private ExportState(int index, String description) {
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

	public static List<ExportState> getAll() {
		return Arrays.asList(ExportState.class.getEnumConstants());
	}

	public static ExportState findByIndex(Integer index) {
		return EnumUtils.getEnum(ExportState.class, index);
	}

	public String getValue() {
		return this.name();
	}

	public String toString() {
		return this.name();
	}

}
