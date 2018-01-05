package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;
/**
 * @描述：IP限制
 * @版本：1.0
 */
public enum IpLimit implements Identifiable<Integer> {
	/** 男 */
	OPEN  (1, "开启"),
	/** 女 */
	CLOSE (2, "关闭");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	private IpLimit(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<IpLimit> getAll() {
		return Arrays.asList(IpLimit.class.getEnumConstants());
	}

	public static IpLimit findByIndex(Integer index) {
		return EnumUtils.getEnum(IpLimit.class, index);
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
