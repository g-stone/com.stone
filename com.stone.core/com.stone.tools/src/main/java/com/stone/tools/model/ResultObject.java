package com.stone.tools.model;

public class ResultObject {
	private int code;
	private String msg;
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public final static int FAILURE = 0;
	public final static int SUCCESS = 1;
	public final static int EMPTYDS = 2;
	public final static int EXCEPTION = 3;
	public final static int LOGEXPIRE = 4;
}
