package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

/**
 * 预约操作类型
 * @ClassName:    BookOperation
 * @Description:  
 * @Author        succ
 * @date          2017-7-27  下午5:34:05
 */
public enum BookOperation implements Identifiable<Integer>  {
	
	CANCEL(1,"取消"),
	BREAK_PROMISE(2,"失约");
	

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private BookOperation(int index, String description) {
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
	
	public static List<BookOperation> getAll() {
		return Arrays.asList(BookOperation.class.getEnumConstants());
	}

	public static BookOperation findByIndex(Integer index) {
		return EnumUtils.getEnum(BookOperation.class, index);
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
