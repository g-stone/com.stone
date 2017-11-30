/**
 * RemoteResponseModel.java
 * @author lixinpeng
 * @DATE: 2017年8月4日 @TIME: 上午11:29:32
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明
 *
 * @author lixinpeng
 * @DATE: 2017年8月4日 @TIME: 上午11:29:32
 *
 */
public class RemoteResponseModel<T> {
	private String message;
	private Integer resultCode;
	private T data;
	private List<T> datas;
	
	public RemoteResponseModel(){
		datas = new ArrayList<T>();
		resultCode = 0;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}
