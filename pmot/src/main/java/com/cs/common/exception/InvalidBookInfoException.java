package com.cs.common.exception;

/**
 * 预约信息错误异常
 * @ClassName:    InvalidBookInfoException
 * @Description:  
 * @Author        succ
 * @date          2017-11-2  下午7:16:44
 */
public class InvalidBookInfoException extends Exception{
	
	private static final long serialVersionUID = 1497357031068007902L;
	
	private String message;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public InvalidBookInfoException() {
		super();
	}
	public InvalidBookInfoException(String message) {
		super();
		this.message = message;
	}
}
