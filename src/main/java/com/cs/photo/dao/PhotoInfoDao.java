package com.cs.photo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cs.photo.dto.PhotoInfoDto;
import com.cs.photo.entity.PhotoInfo;

/**
 * 照片信息DAO
 * @ClassName:    PhotoInfoDao
 * @Description:  
 * @Author        succ
 * @date          2017-8-25  上午9:43:22
 */
public interface PhotoInfoDao {
	public List<PhotoInfo> list(@Param("photoInfo")PhotoInfoDto photoInfo,@Param("types")List<String> allowAccessPhotoTypes);
}
