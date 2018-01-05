/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.math.BigDecimal;

import com.cs.common.utils.database.ObjectTransformer;

/**
 * @创建时间：2012-9-27 下午03:03:50
 * @创建人：xutietie
 * @version: 1.0
 * @描述:
 */
public class NumberTransformer implements ObjectTransformer {

	@Override
	public Object transformer(Object value) throws Exception {
		String str = (String) value;
		if (null == str || str.length() == 0) {
			return null;
		}
		BigDecimal bd = new BigDecimal(str);
		// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
		// bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd;
	}
}
