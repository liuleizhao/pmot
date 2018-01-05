/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;

import com.cs.common.utils.database.StringTransformer;

/**
 * @创建时间：2012-9-27 下午02:35:26
 * @创建人：xutietie
 * @version: 1.0
 * @描述:Clob转换为String
 */
public class Clob2StringTransformer implements StringTransformer {

	@Override
	public String transformer(Object value) throws Exception {
		Clob clob = (Clob) value;
		 Reader is = clob.getCharacterStream();// 得到流
		 BufferedReader br = new BufferedReader(is);
		 String s = br.readLine();
		StringBuffer bf = new StringBuffer();
		 while (s != null) {//
		// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		 bf.append(s);
		 s = br.readLine();
		 }
		return bf.toString();
	}
}
