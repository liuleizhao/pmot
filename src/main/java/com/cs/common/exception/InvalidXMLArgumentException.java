package com.cs.common.exception;

/**
 * XML参数异常
 * @ClassName:    InvalidXMLArgumentException
 * @Description:  
 * @Author        succ
 * @date          2017-10-28  下午3:33:51
 */
public class InvalidXMLArgumentException extends Exception{
	
	private static final long serialVersionUID = -438582105550892506L;
	
	private String message;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public InvalidXMLArgumentException() {
		super();
	}
	public InvalidXMLArgumentException(String message) {
		super();
		this.message = message;
	}
}
