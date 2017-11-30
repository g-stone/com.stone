/**
 * HttpRemoteRequest.java
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:49:28
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

/**
 * 功能说明：HTTP请求实现
 *
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:49:28
 */
public class HttpRemoteRequest extends AbstractRemoteRequest{
	private static boolean isInited = false;
	private static HttpRemoteRequest instance = null;
	private static CloseableHttpClient httpClient = null;
	
	private static Logger logger = Logger.getLogger(HttpRemoteRequest.class);
	
	private HttpRemoteRequest(){}
	
	private static HttpRemoteRequest getInstance(int socketTimeout, int connectTimeout){
		if(!isInited){
			init(socketTimeout, connectTimeout);
		}
		
		return instance;
	}
	
	public static HttpRemoteRequest getInstance(){
		return getInstance(defaultSocketTimeout, defaultConnectTimeout);
	}
	
	private static void init(int socketTimeout, int connectTimeout){
		instance = new HttpRemoteRequest();
		try{
			instance.resetRequestConfig(socketTimeout, connectTimeout);
			httpClient = HttpClients.createDefault();
			isInited = true;
		}catch(Exception e){
			logger.error("初始化异常------->", e);
		}
	}
	
	private void resetRequestConfig(int socketTimeout, int connectTimeout){
		requestConfig = RequestConfig
				.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout)
				.build();
	}
	
	@Override
	protected RequestConfig getRequestConfig(){
		return requestConfig;
	}
	
	@Override
	protected CloseableHttpClient getClient() {
		return httpClient;
	}
}
