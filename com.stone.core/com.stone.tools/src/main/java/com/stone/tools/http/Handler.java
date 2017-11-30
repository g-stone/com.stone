/**
 * Handler.java
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:47:45
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

/**
 * 功能说明：apache http请求框架响应处理接口
 *
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:47:45
 */
public abstract class Handler<T> implements ResponseHandler<T>{
	protected Class<?> realType;
	protected boolean level;
	
	public Handler(Class<?> realType, boolean level){
		this.realType = realType;
		this.level = level;
	}
	
	/**
	 * 功能说明：解析返回内容
	 * Handler.handle();
	 * @author: lixinpeng
	 * @DATE: 2017年10月19日  @TIME: 上午9:56:21
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected T handle(HttpResponse response) throws Exception{
		return handleResponse(response);
	}
}
