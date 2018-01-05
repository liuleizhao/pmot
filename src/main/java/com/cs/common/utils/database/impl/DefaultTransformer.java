package com.cs.common.utils.database.impl;

import com.cs.common.utils.database.Transformer;

/**
 * @创建时间：2012-9-12 下午04:45:27
 * @创建人：xutietie
 * @version: 1.0
 * @描述:默认转换器
 */
public class DefaultTransformer implements Transformer {

	public String transform(Object value) {
		String str = null;
		if (null == value) {
			return "";
		}
		try {
			if (value.getClass().getName().toUpperCase()
					.indexOf("ORACLE.SQL.TIMESTAMP") != -1) {// oracle.sql.TIMESTAMP
				Time2StringTransformer time = new Time2StringTransformer();
				str = time.transformer(value);
			} else if (value.getClass().getName().toUpperCase()
					.indexOf("JAVA.SQL.TIMESTAMP") != -1) {// date
				Date2StringTransformer date = new Date2StringTransformer();
				str = date.transformer(value);
			} else if (value.getClass().getName().toUpperCase()
					.indexOf("STRING") != -1) {// varchar,long
				String2StringTransformer string = new String2StringTransformer();
				str = string.transformer(value);
			} else if (value.getClass().getName().toUpperCase()
					.indexOf("BIGDECIMAL") != -1) {// number
				Number2StringTransformer number = new Number2StringTransformer();
				str = number.transformer(value);
			} else if (value.getClass().getName().toUpperCase().indexOf("CLOB") != -1) {// CLOB
				Clob2StringTransformer clob = new Clob2StringTransformer();
				str = clob.transformer(value);
			} else if (value.getClass().getName().toUpperCase().indexOf("BLOB") != -1) {// BLOB
				Blob2StringTransformer blob = new Blob2StringTransformer();
				str = blob.transformer(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public Object transform(Object value, String target) {
		if (null == value) {
			return null;
		}
		Object object = null;
		try {
			if (target.toUpperCase().indexOf("TIMESTAMP") != -1) {
				TimestampTransformer timestamp = new TimestampTransformer();
				object = timestamp.transformer(value);
			} else if (target.toUpperCase().indexOf("DATE") != -1) {
				DateTransformer date = new DateTransformer();
				object = date.transformer(value);
			} else if (target.toUpperCase().indexOf("VARCHAR2") != -1) {
				object = value;
			} else if (target.toUpperCase().indexOf("NUMBER") != -1) {
				NumberTransformer number = new NumberTransformer();
				object = number.transformer(value);
			} else if (target.toUpperCase().indexOf("LONG") != -1) {
				LongTransformer lon = new LongTransformer();
				object = lon.transformer(value);
			} else if (target.toUpperCase().indexOf("BLOB") != -1) {
				BlobTransformer blob = new BlobTransformer();
				object = blob.transformer(value);
			} else if (target.toUpperCase().indexOf("CLOB") != -1) {
				ClobTransformer clob = new ClobTransformer();
				object = clob.transformer(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
