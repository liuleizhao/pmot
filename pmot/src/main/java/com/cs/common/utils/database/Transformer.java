package com.cs.common.utils.database;

/**
 * @创建时间：2012-9-12 下午04:36:07
 * @创建人：xutietie
 * @version: 1.0
 * @描述:用于对象间的转换
 */
public interface Transformer {

	/**
	 * 转换为字符串
	 * 
	 * @param value
	 * @return
	 */
	public String transform(Object value);

	public Object transform(Object value, String target);
}

