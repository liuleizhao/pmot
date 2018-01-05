/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.sql.Timestamp;
import java.text.ParseException;

import com.cs.common.utils.DateUtil;
import com.cs.common.utils.database.ObjectTransformer;

/**
 * @创建时间：2012-9-27 下午03:05:48
 * @创建人：xutietie
 * @version: 1.0
 * @描述:字符串转换为TimeStamp
 */
public class TimestampTransformer implements ObjectTransformer {

	@Override
	public Object transformer(Object value) throws Exception {
		Timestamp timestamp = null;
		try{
			timestamp = DateUtil.str2Timestamp((String) value,"yyyy-MM-dd HH:mm:ss.SSS");
		}catch(ParseException e){
			timestamp = DateUtil.str2Timestamp((String) value,"yyyy-MM-dd HH:mm:ss");
		}
		return timestamp;
	}
}
