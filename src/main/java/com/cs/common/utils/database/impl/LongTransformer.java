/**
 * 
 */
package com.cs.common.utils.database.impl;

import com.cs.common.utils.database.ObjectTransformer;

/**
 * @创建时间：2012-9-27 下午03:05:02
 * @创建人：xutietie
 * @version: 1.0
 * @描述:
 */
public class LongTransformer implements ObjectTransformer {

	@Override
	public Object transformer(Object value) throws Exception {
		Long lon = new Long((String) value);
		return lon;
	}
}
