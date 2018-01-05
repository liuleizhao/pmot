package com.cs.common.entityenum;

import java.util.Arrays;
import java.util.List;

import com.cs.common.utils.EnumUtils;
import com.cs.mvc.mybatis.enumhandler.Identifiable;

public enum CheckState implements Identifiable<String> {

	/** 检验通过：19，30，31，32 ,99
	 *  检验不通过：16，17,18  
	 *  退办：99*/
	DDJYKS("00","等待检验开始"),
	JYGCKS("01", "检验过程开始，等待检验结果上传"),
	JYGCJS("10","检验过程结束，等待其他资料和结果上传"),
	JYJGSC("14","检验结果 和资料上传完成，等待监管系统检验"),
	JGXTZZSH("15","监管系统正在检验审核"),
	JGXTSHBTG("16","监管系统检验审核不通过"),
	JGXTSHBTG2("17","监管系统检验审核不通过"),
	JDCAQJYZCBHG("18","机动车安全技术检验报告整车判定为不合格"),
	DDZHYYPTFH("19","监管系统检验审核通过，等待综合应用平台复核"),
	JGZTDFH("20","监管系统待复核"),
	DDFJKS("21","等待复检开始"),
	ZHPTSHTG("30","综合应用平台审核通过，等待检验机构打印"),
	JYZGYDY("31","检验机构已打印，等待监管系统归档"),
	JGXTYGD("32","监管系统已归档，等待综合应用平台归档"),
	YWTB("90","业务退办"),
	YWBJ("99","业务办结");

	/** 顺序 */
	private String index;

	/** 描述 */
	private String description;

	private CheckState(String index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public String getIndex() {
		return this.index;
	}

	public String getId() {
		return this.index;
	}
	
	public static List<CheckState> getAll() {
		return Arrays.asList(CheckState.class.getEnumConstants());
	}

	public static CheckState findByIndex(String index) {
		return EnumUtils.getEnum(CheckState.class, index);
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
