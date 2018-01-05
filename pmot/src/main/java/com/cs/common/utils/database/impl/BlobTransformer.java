/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.cs.common.utils.database.ObjectTransformer;

/**
 * @创建时间：2012-9-27 下午03:03:02
 * @创建人：xutietie
 * @version: 1.0
 * @描述:字符串转换为Blob
 */
public class BlobTransformer implements ObjectTransformer {

	@Override
	public Object transformer(Object value) throws Exception {
		String str = (String) value;
		InputStream input = new ByteArrayInputStream(str.getBytes());
		return input;
	}
}
