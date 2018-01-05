package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum SyncState implements Identifiable<Integer> {

	WAIT_SYNC(0, "待同步"),
	
	FINISH_SYNC(1, "已同步");

	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;
	
	private SyncState(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public int getIndex() {
		return this.index;
	}

	public static List<SyncState> getAll() {
		return Arrays.asList(SyncState.class.getEnumConstants());
	}

	public static SyncState findByIndex(Integer index) {
		return EnumUtils.getEnum(SyncState.class, index);
	}
	
	@Override
	public Integer getId() {
		return this.getIndex();
	}
	@Override
	public String getDescription() {
		return this.description;
	}

}
