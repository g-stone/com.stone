/**
 * RemoteRequest.java
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:43:35
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http;

/**
 * 功能说明：远程请求API
 *
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:43:35
 *
 */
public interface RemoteRequest {
	/**
	 * 功能说明：执行请求操作
	 * RemoteRequest.execute();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:46:57
	 * @param parameter
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	<T> T execute(RequestParameter parameter, Handler<T> handler) throws Exception;
}
