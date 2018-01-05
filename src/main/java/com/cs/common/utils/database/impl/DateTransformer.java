/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.text.ParseException;
import java.util.Date;

import com.cs.common.utils.DateUtil;
import com.cs.common.utils.database.ObjectTransformer;

/**
 * @创建时间：2012-9-27 下午03:05:17
 * @创建人：xutietie
 * @version: 1.0
 * @描述:字符串转换为Date
 */
public class DateTransformer implements ObjectTransformer {

	@Override
	public Object transformer(Object value) throws Exception {
		Date date = null;
		try{
			date = DateUtil.str2Date((String) value, "yyyy-MM-dd HH:mm:ss");
		}catch(ParseException e){
			date = DateUtil.str2Date((String) value, "yyyy-MM-dd");
		}
		return date;
	}
}
