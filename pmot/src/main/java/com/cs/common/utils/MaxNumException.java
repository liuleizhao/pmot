package com.cs.common.utils;

/**
 * 已预约人数大于最大预约数异常
 * @author wangj
 *
 */
public class MaxNumException extends Exception{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 5268414158489522723L;

	public MaxNumException(){
		super();
	}
	
	public MaxNumException(String msg){
		super(msg);
	}
	 

}
