package com.cs;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.cs.appoint.dao.BookInfoCompatibleDao;
import com.cs.appoint.dao.BookInfoDao;
import com.cs.appoint.dao.VehIsFlowDao;
import com.cs.appoint.entity.BookInfoCompatible;
import com.cs.common.utils.DateUtil;
import com.cs.common.utils.DateUtils;

@ContextConfiguration("classpath:application-context.xml")
public class TestBookInfoDao extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private BookInfoDao bookInfoDao;
	@Autowired
	private VehIsFlowDao vehIsFlowDao;
	@Autowired
	private BookInfoCompatibleDao bookInfoCompatibleDao;
	
	@Test
	public void findFinishedBookAmountGroupByStation() throws ParseException{
		Date ymd = DateUtil.getDateYYMMDD(new Date());
		ymd = DateUtils.parseDate("2017-11-30");
		BookInfoCompatible b = bookInfoCompatibleDao.findByStationIdAndDate("5CEA951EE4B36510E0536B01A8C0B332", ymd);
		System.out.println(b);
	}
	
}
