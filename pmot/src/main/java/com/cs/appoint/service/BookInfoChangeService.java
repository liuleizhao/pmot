package com.cs.appoint.service;

import com.cs.appoint.entity.BookInfo;
import com.cs.appoint.entity.BookInfoChange;
import com.cs.mvc.service.BaseService;

public interface BookInfoChangeService extends BaseService<BookInfoChange, String>{
	
	Integer insert(BookInfo bookInfo);

}
