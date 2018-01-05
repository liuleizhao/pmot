package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum VehicleCharacter  implements Identifiable<Integer> {
	XXZK(1, "小型载客汽车"),
	ZZZH(2, "小型载货汽车(含专项作业车)"),
	XXXC(3, "校车(小型)"),
	ZXZK(4, "中型载客汽车"),
	DXZK(5," 大型载客汽车"),
	MZXZH(6, "中型载货汽车"),
	ZXZH(7, "重型载货汽车"),
	ZXZY(8, "专项作业车"),
	DXXC(9, "校车(大型)"),
	WGDC(10, "无轨电车"),
	PTGC(11, "普通挂车"),
	DSZH(12, "低速载货汽车"),
	WHPZSC(13, "危化品运输车"),
	;
	
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private VehicleCharacter(int index, String description) {
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
	
	public static List<VehicleCharacter> getAll() {
		return Arrays.asList(VehicleCharacter.class.getEnumConstants());
	}

	public static VehicleCharacter findByIndex(Integer index) {
		return EnumUtils.getEnum(VehicleCharacter.class, index);
	}
	


}
