package com.cs.common.utils;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class RandomValue {

	
	
	private static final String[] numValues = { "0", "1", "2", "3",
		"4", "5", "6", "7", "8", "9"};
	private static final String[] totalValues = {"A", "B", "C", "D",
		"E", "F", "H", "0", "1", "2", "3",
		"4", "5", "6", "7", "8", "9"};
	
	private static final Random random= new Random();
	
	public static String getRandomValue(String prefix) {
		String value="" ;

		while(StringUtils.isBlank(value)|| StringUtils.isNotBlank(RedisUtil.getValue(prefix+value))){
			value = "";
			for(int i = 0 ;i<3 ; i++ ){
				value += numValues[random.nextInt(10)];
			}
			value  += totalValues[random.nextInt(17)];
			
		}
		RedisUtil.setValue(prefix+value, value, 60*60);//保存一小时 
		return value;
	}
}
