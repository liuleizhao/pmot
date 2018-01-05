/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.math.BigDecimal;

import com.cs.common.utils.database.StringTransformer;

/**
 * @创建时间：2012-9-27 下午02:35:51
 * @创建人：xutietie
 * @version: 1.0
 * @描述:
 */
public class Number2StringTransformer implements StringTransformer {

	@Override
	public String transformer(Object value) throws Exception {
		BigDecimal decimal = (BigDecimal) value;
		return decimal.toString();
	}
}
