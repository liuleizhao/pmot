package com.cs.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @描叙：日期工具包
 * @作者：肖佳
 * @开发日期：2011-8-3
 * @版权:永泰软件有限公司
 * @版本：1.0
 */
public class DateUtil {

	private static String timePattern = "HH:mm";

	private static String datePattern = "yyyy-MM-dd HH:mm";

	private static String dayPattern = "yyyy-MM-dd";

	private static String dateChinaPattern = "yyyy年MM月dd日";
	
	private static Calendar calendar = Calendar.getInstance();

	public static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	public final static SimpleDateFormat DATEFORMAT_1 = new SimpleDateFormat(
			"MM月dd日");

	public final static SimpleDateFormat TIMEFORMAT_1 = new SimpleDateFormat(
			"HH:ss");

	public final static String EMPTY = "";
	
	/**
	 * 默认日期格式
	 */
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	// yyyy-MM-dd HH:mm
	public static String getDateStr(Date theTime) {
		return getDateTime(datePattern, theTime);
	}

	/**
	 * @param theTime
	 * @return yyyy-MM-dd
	 */
	public static String getDayStr(Date theTime) {
		return getDateTime(dayPattern, theTime);
	}

	/**
	 * 将指定日期以“xxxx年xx月xx日”的中文格式输出
	 * 
	 * @param theTime
	 * @return MSY 2008-6-5
	 */
	public static String getDateStrYYMMDD(Date theTime) {
		return getDateTime(dateChinaPattern, theTime);
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = EMPTY;

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static boolean isOneDay(String days) {
		final Date now = new Date();
		String nowtime = df.format(now).toString();
		return nowtime.equals(days);
	}

	public static boolean isToday(java.sql.Date aDate) {
		final Date now = new Date();
		String nowtime = df.format(now).toString();
		String createtime = df.format(aDate).toString();
		return nowtime.equals(createtime);
	}

	public static boolean isToday(java.util.Date aDate) {
		final Date now = new Date();
		String nowtime = df.format(now).toString();
		String createtime = df.format(aDate).toString();
		return nowtime.equals(createtime);
	}

	public static boolean isToday(java.sql.Timestamp aDate) {
		final Date now = new Date();
		String nowtime = df.format(now).toString();
		String createtime = df.format(aDate).toString();
		return nowtime.equals(createtime);
	}

	public static Date addDate(Date date, int type, int intervel) {
		if (date == null) {
			date = new Date();
		}
		calendar.setTime(date);
		calendar.add(type, intervel);
		return calendar.getTime();
	}

	/**
	 * 获取现在的年份
	 * 
	 * @return
	 */
	public static int getNowYear() {
		return calendar.get(Calendar.YEAR);
	}

	public static String getYear(Date date) {
		return getDateTime("yyyy", date);
	}

	/**
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static int getYear(String date){
		 
		try {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static int getMonth(String date){
		try {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.get(Calendar.MONTH)+1;
	}
	/**
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static int getDay(String date){
		try {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 返回yyyy-MM-dd格式日期
	 * @param cal
	 * @return
	 */
	public static String getDate(Calendar cal){
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		int day=cal.get(Calendar.DAY_OF_MONTH);
		String month_s,day_s;
		if(month<10){
			month_s="0"+month;
		}else{
			month_s=String.valueOf(month);
		}
		if(day<10){
			day_s="0"+day;
		}else{
			day_s=String.valueOf(day);
		}
		return year+"-"+month_s+"-"+day_s;
		
	}
	public long getDiffDays(String date) throws ParseException
	{
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date acceptdate = sdf.parse(date);
		long t = new Date().getTime() - acceptdate.getTime();
		return t / 86400000L;
		  
	}
	
	/**
	 * 比较两个时间的大小
	 * 
	 * @param time
	 * @return
	 */
	public long compareTime(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long days = -2;
		try {
			Date d1 = df.parse(time);
			String newDate = df.format(new Date());
			Date d2 = df.parse(newDate);
			long diff = d1.getTime() - d2.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}
	
	public static String getweek(String date) throws ParseException{
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	     Date strtodate = formatter.parse(date);  
		 SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		return sdf.format(strtodate.getTime());
	}
	
	/**
	 * 时间戳转换为字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String timestamp2Str(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(date, DEFAULT_FORMAT);
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 * @throws ParseException 
	 */
	public static Timestamp str2Timestamp(String str) throws ParseException {
		Date date = str2Date(str, DEFAULT_FORMAT);
		return new Timestamp(date.getTime());
	}
	
	public static Timestamp str2Timestamp(String str,String format) throws ParseException {
		Date date = str2Date(str, format);
		return new Timestamp(date.getTime());
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(addMinutes("23:20",300));
	}
	
	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 日期
	 * @throws java.text.ParseException
	 */
	public static Date str2Date(String str, String format) throws ParseException {
		if (null == str || "".equals(str)) {
			return null;
		}
		// 如果没有指定字符串转换的格式，则用默认格式进行转换
		if (null == format || "".equals(format)) {
			format = DEFAULT_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 添加指定分钟数
	 * @param timeStr
	 * @param minutes
	 * @return
	 * @throws ParseException
	 * @Author        succ
	 * @date 2017-11-1 下午6:15:56
	 */
	public static String addMinutes(String timeStr,int minutes) throws ParseException{
		if(minutes == 0){
			return timeStr;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = sdf.parse(timeStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int preDayOfYear = c.get(Calendar.DAY_OF_YEAR);
		c.add(Calendar.MINUTE, minutes);
		int curDayOfYear = c.get(Calendar.DAY_OF_YEAR);
		
		if(preDayOfYear != curDayOfYear){ 
			if(minutes > 0){//如果大于当天的范围，则返回"23:59"
				return "23:59";
			}else{//如果小于当天的范围，则返回"00:00"
				return "00:00";
			}
		}
		return sdf.format(c.getTime());
	}
	
	/**
	 * 保留指定日期中的年月日，其余部分清零
	 * @param date
	 * @return
	 */
	public static Date getDateYYMMDD(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 将时分秒,毫秒域清零
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}