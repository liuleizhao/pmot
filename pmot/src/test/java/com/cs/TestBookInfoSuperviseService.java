package com.cs;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.cs.appoint.entity.BookInfoSupervise;
import com.cs.appoint.service.BookInfoSuperviseService;

@ContextConfiguration("classpath:application-context.xml")
public class TestBookInfoSuperviseService extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private BookInfoSuperviseService bookInfoSuperviseService;
	
	@Test
	public void findBookInfoNoRecord(){
		List<BookInfoSupervise> list = bookInfoSuperviseService.findBookInfoException(null);
		System.out.println(list.size());
	}
}
