/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

import com.cs.common.utils.DateUtil;
import com.cs.common.utils.database.StringTransformer;

/**
 * @创建时间：2012-9-27 下午02:36:41
 * @创建人：xutietie
 * @version: 1.0
 * @描述:date,TIMESTAMP转换为字符串
 */
public class Time2StringTransformer implements StringTransformer {

	@Override
	public String transformer(Object value) throws Exception {
		String str = null;
		Class clz = value.getClass();
		Method method = clz.getMethod("timestampValue", null);
		// method = clz.getMethod("timeValue", null); 时间类型
		// method = clz.getMethod("dateValue", null); 日期类型
		Timestamp time = (Timestamp) method.invoke(value, null);
		Date date = new Date(time.getTime());
		str = DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss.SSS");
		return str;
	}
}
