package com.cs.photo.service;

import java.util.List;

import com.cs.photo.dto.PhotoInfoDto;
import com.cs.photo.entity.PhotoInfo;

/**
 * 照片信息Service
 * @ClassName:    PhotoInfoService
 * @Description:  
 * @Author        succ
 * @date          2017-8-28  上午10:43:51
 */
public interface PhotoInfoService {
	public List<PhotoInfo> list(PhotoInfoDto photoInfo);
	
	public String getPhotoByXML(String argsXML);
}
