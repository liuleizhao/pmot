/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.io.Reader;
import java.io.StringReader;

import com.cs.common.utils.database.ObjectTransformer;

/**
 * @创建时间：2012-9-27 下午03:03:27
 * @创建人：xutietie
 * @version: 1.0
 * @描述:字符串转换为Clob
 */
public class ClobTransformer implements ObjectTransformer {

	@Override
	public Object transformer(Object value) throws Exception {
		String str = (String) value;
		Reader input = new StringReader(str); // 将 text转成流形式
		return input;
	}
}
