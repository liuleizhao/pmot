package com.cs.appoint.service;

import java.util.List;

import com.cs.appoint.entity.BookInfoSupervise;

/**
 * 预约信息事后监管Service
 * @ClassName:    BookInfoSuperviseService
 * @Description:  
 * @Author        succ
 * @date          2017-11-4  下午5:48:15
 */
public interface BookInfoSuperviseService {
	public List<BookInfoSupervise> findBookInfoException(BookInfoSupervise bookInfoSupervise);
}
