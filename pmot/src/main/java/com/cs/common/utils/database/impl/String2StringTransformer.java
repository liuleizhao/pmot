/**
 * 
 */
package com.cs.common.utils.database.impl;

import com.cs.common.utils.database.StringTransformer;

/**
 * @创建时间：2012-9-27 下午02:36:25
 * @创建人：xutietie
 * @version: 1.0
 * @描述:varchar2,long类型转换为String
 */
public class String2StringTransformer implements StringTransformer {

	@Override
	public String transformer(Object value) throws Exception {
		String str = null;
		str = (String) value;
		return str;
	}
}
