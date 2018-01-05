package com.cs.webservice.dto;

/**
 * WebService专用 -封装Head部分XML
 * @ClassName:    XmlHeadVo
 * @Description:  
 * @Author        succ
 * @date          2017-11-2  上午10:43:49
 */
public class XmlHeadVo {
	
	/** 系统类别 */
	private String xtlb;
	/** 接口ID */
	private String jkid;
	/** 接口序列号 */
	private String jkxlh;
	
	public String getXtlb() {
		return xtlb;
	}
	public void setXtlb(String xtlb) {
		this.xtlb = xtlb;
	}
	public String getJkid() {
		return jkid;
	}
	public void setJkid(String jkid) {
		this.jkid = jkid;
	}
	public String getJkxlh() {
		return jkxlh;
	}
	public void setJkxlh(String jkxlh) {
		this.jkxlh = jkxlh;
	}
}
