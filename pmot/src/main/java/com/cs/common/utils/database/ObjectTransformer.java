/**
 * 
 */
package com.cs.common.utils.database;

/**
 * @创建时间：2012-9-27 下午03:01:17
 * @创建人：xutietie
 * @version: 1.0
 * @描述:字符串转换为对象
 */
public interface ObjectTransformer {
	public Object transformer(Object value) throws Exception;
}
