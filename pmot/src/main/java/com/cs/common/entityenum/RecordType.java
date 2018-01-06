package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.mvc.mybatis.enumhandler.Identifiable;
import com.cs.common.utils.EnumUtils;
/**
 * 黑名单类型
 * @ClassName:    RecordType
 * @Description:  
 * @Author        succ
 * @date          2017-7-27  下午5:33:56
 */
public enum RecordType  implements Identifiable<Integer>{
	
	MOBILE(1,"手机号"),
	PLATE_NUMBER(2,"号牌号码"),
	IP(3,"IP地址"),
	IDNUMBER(4,"证件号"),
	;

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private RecordType(int index, String description) {
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
	
	public static List<RecordType> getAll() {
		return Arrays.asList(RecordType.class.getEnumConstants());
	}

	public static RecordType findByIndex(Integer index) {
		return EnumUtils.getEnum(RecordType.class, index);
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
