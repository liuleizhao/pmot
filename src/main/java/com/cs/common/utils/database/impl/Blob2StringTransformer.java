/**
 * 
 */
package com.cs.common.utils.database.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import oracle.sql.BLOB;

import com.cs.common.utils.database.StringTransformer;


/**
 * @创建时间：2012-9-27 下午02:35:04
 * @创建人：xutietie
 * @version: 1.0
 * @描述:Blob转换为String
 */
public class Blob2StringTransformer implements StringTransformer {

	@Override
	public String transformer(Object value) throws Exception {
		StringBuffer bf = new StringBuffer();
		BLOB blob = (BLOB) value;
		InputStream in = blob.getBinaryStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String s = null;
		while ((s = br.readLine()) != null) {
			bf.append(s);
		}
		in.close();
		isr.close();
		br.close();
		return bf.toString();
	}
}
