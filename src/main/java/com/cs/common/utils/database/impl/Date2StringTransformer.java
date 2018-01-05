/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.sql.Timestamp;

import com.cs.common.utils.DateUtil;
import com.cs.common.utils.database.StringTransformer;


/**
 * @创建时间：2012-9-27 下午05:59:31
 * @创建人：xutietie
 * @version: 1.0
 * @描述:
 */
public class Date2StringTransformer implements StringTransformer {

	@Override
	public String transformer(Object value) throws Exception {
		Timestamp date = (Timestamp) value;
		String str = DateUtil.timestamp2Str(date);
		return str;
	}
}
