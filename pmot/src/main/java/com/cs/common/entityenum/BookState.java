package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

/**
 * 网上预约状态枚举
 * 
 * @author 肖佳
 * 
 */
public enum BookState implements Identifiable<Integer> {

	/** 预约中 */
	YYZ(1, "预约中"),

	/** 预约完成 */
	YYWC(2, "预约完成"),

	/** 失约 */
	SY(3, "失约"),

	/** 预约取消 */
	YYQX(4, "预约取消"),

	/** 由于预约手机号码进入黑名单，将对应的信息锁定 */
	BS(5," 被锁"),
	
	/** 作废 */
	ZF(6," 作废"),
	;
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private BookState(int index, String description) {
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
	
	public static List<BookState> getAll() {
		return Arrays.asList(BookState.class.getEnumConstants());
	}

	public static BookState findByIndex(Integer index) {
		return EnumUtils.getEnum(BookState.class, index);
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
