package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum OrgType implements Identifiable<Integer> {
	CHECK_STATION(1,"检测站"),SERVICE_STATION(2,"服务站");
	
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	private OrgType(int index,String description){
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
	
	public static List<OrgType> getAll() {
		return Arrays.asList(OrgType.class.getEnumConstants());
	}

	public static OrgType findByIndex(Integer index) {
		return EnumUtils.getEnum(OrgType.class, index);
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
