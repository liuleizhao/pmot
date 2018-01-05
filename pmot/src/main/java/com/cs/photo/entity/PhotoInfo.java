package com.cs.photo.entity;

import javax.xml.bind.annotation.XmlRootElement;



/**
 * 照片信息实体
 * @ClassName:    PhotoInfo
 * @Description:  
 * @Author        succ
 * @date          2017-8-25  上午9:43:31
 */
@XmlRootElement(name="PhotoInfo")
public class PhotoInfo {
	/** 流水号 */
	private String serialNum;
	/** 照片序号 */
	private Integer photoIndex;
	/** 照片信息 */
	private byte[] photo;
	/** 照片类型代码 */
	private String photoType;
	/** 校验位 */
	private String jyw;
	/** 传输标记 */
	private Integer transferMark;
	
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public Integer getPhotoIndex() {
		return photoIndex;
	}
	public void setPhotoIndex(Integer photoIndex) {
		this.photoIndex = photoIndex;
	}
	public String getPhotoType() {
		return photoType;
	}
	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
	public String getJyw() {
		return jyw;
	}
	public void setJyw(String jyw) {
		this.jyw = jyw;
	}
	public Integer getTransferMark() {
		return transferMark;
	}
	public void setTransferMark(Integer transferMark) {
		this.transferMark = transferMark;
	}
	@Override
	public String toString() {
		return "PhotoInfo [serialNum=" + serialNum + ", photoIndex="
				+ photoIndex + ", photoType=" + photoType + ", jyw=" + jyw
				+ ", transferMark=" + transferMark + "]";
	}
	
}