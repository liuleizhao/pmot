package com.cs.common.utils.webservice;

import org.apache.commons.lang3.StringUtils;

/**
 * 验证专用类 
 * @author vincent
 *
 */
public class ValidateUtil {

	
	public static boolean  validateIsBlank(String... values){
		
		for(String value : values){
			if(StringUtils.isBlank(value)){
				return true;
			}
		}
		return  false;
	}
	
	
	
	
	
}
