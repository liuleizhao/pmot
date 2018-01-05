package com.cs.common.utils.webservice;

public class ResponseMessage {
		
	private String code; // 只有返回为"00"时属于正常返回状态 

	private String msg ;  // 非"00" 时候调取消息用于显示 
	
	private Object result; // 消息主体

	public ResponseMessage(String code , String  msg  ,Object  result){
		this.code = code;
		this.msg = msg ;
		this.result = result;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
