/**
 * AbstractRemoteRequest.java
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午2:06:21
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import com.stone.tools.ConverteUtils;

/**
 * 功能说明：HTTP请求抽象类
 *
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午2:06:21
 */
public abstract class AbstractRemoteRequest implements RemoteRequest{
	private static Logger logger = Logger.getLogger(AbstractRemoteRequest.class);
	/**默认发起请求方式*/
	protected final static String DEFAULT_REQUEST_TYPE = "POST";
	/**默认请求格式*/
	protected final static String DEFAULT_RESPONSE_TYPE = "json";
	/**默认请求编码*/
	protected final static String DEFAULT_RESPONSE_CHARSET = "UTF-8";
	/**默认套接字超时时间*/
	protected static int defaultSocketTimeout = 10000;
	/**默认请求超时时间*/
	protected static int defaultConnectTimeout = 30000;
	/**请求配置对象*/
	protected RequestConfig requestConfig;
	
	public <T> T execute(RequestParameter parameter, Handler<T> handler) throws Exception{
		return fire(handler, parseRequestParameter(parameter));
	}
	
	/**
	 * 功能说明：请求参数解析，解析出请求参数和请求的业务参数
	 * AbstractRemoteRequest.parseRequestParameter();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:19:42
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	protected Object[] parseRequestParameter(RequestParameter parameter) throws Exception{
		Object[] parameters = new Object[5];
		if(StringUtils.isBlank(parameter.getRequestUrl())){
			throw new Exception("请求地址不能为空....");
		}
		parameters[0] = parameter.getRequestUrl();
		if(StringUtils.isBlank(parameter.getRequestType())){
			parameters[1] = DEFAULT_REQUEST_TYPE;
		}else{
			parameters[1] = parameter.getRequestType().toUpperCase();
		}
		if(StringUtils.isBlank(parameter.getResponseType())){
			parameters[2] = DEFAULT_RESPONSE_TYPE;
		}else{
			parameters[2] = parameter.getResponseType().toLowerCase();
		}
		if(StringUtils.isBlank(parameter.getResponseCharset())){
			parameters[3] = DEFAULT_RESPONSE_CHARSET;
		}else{
			parameters[3] = parameter.getResponseCharset();
		}
		parameters[4] = parameter.getRequestParameters();
		
		return parameters;
	}
	
	/**
	 * 功能说明：建立HTTP请求对象
	 * AbstractRemoteRequest.buildRequest();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:20:46
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected HttpUriRequest buildRequest(Object[] requestParameter) throws Exception{
		HttpUriRequest request = null;
		switch(String.valueOf(requestParameter[1])){
			case "POST":
				HttpPost httpPost = new HttpPost(String.valueOf(requestParameter[0]));
				String content = "";
				switch(String.valueOf(requestParameter[2])){
					case "json":
						httpPost.addHeader("Content-Type", "application/json");
						content = ConverteUtils.toJson(requestParameter[4]);
						break;
					case "xml":
						httpPost.addHeader("Content-Type", "text/xml");
						content = ConverteUtils.toXmlString("", requestParameter[4]);
						break;
					default:
						httpPost.addHeader("Content-Type", "application/json");
						content = ConverteUtils.toJson(requestParameter[4]);
				}
				StringEntity postEntity = new StringEntity(content, String.valueOf(requestParameter[3]));
				httpPost.setEntity(postEntity);
				httpPost.setConfig(getRequestConfig());
				
				logger.info("*************************API，POST请求*************************"
						+ "\n\t发送地址：" + httpPost.getRequestLine() 
						+ "\n\t发送参数内容：" + content 
						+ "\n\t内容格式：" + requestParameter[2]);
				request = httpPost;
				break;
			case "GET":
				URIBuilder builder = new URIBuilder(String.valueOf(requestParameter[0]));
				Map<String, Object> param = (Map<String, Object>)requestParameter[4];
				for(Entry<String, Object> entry:param.entrySet()){
					builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
				}
				HttpGet httpGet = new HttpGet(builder.build());
				httpGet.setConfig(getRequestConfig());
				
				logger.info("*************************API，GET请求*************************"
						+ "\n\t发送地址：" + httpGet.getRequestLine());
				request = httpGet;
				break;
		}
		return request;
	}
	
	/**
	 * 功能说明：发起HTTP请求操作
	 * AbstractRemoteRequest.fire();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:45:12
	 * @param handler
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 */
	protected <T> T fire(Handler<T> handler, Object[] requestParameter) throws Exception{
		T responseEntity = null;
		HttpUriRequest request = buildRequest(requestParameter);
		CloseableHttpClient client = getClient();
		if(client == null){
			logger.info("************************************************\n\t"
					+ "请求客户端对象为空\n\t"
					+ "************************************************");
		}else{
			responseEntity = client.execute(request, handler);
		}
		
		return responseEntity;
	}
	
	/**
	 * 功能说明：获取请求的配置信息
	 * AbstractRemoteRequest.getRequestConfig();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:45:41
	 * @return
	 */
	protected abstract RequestConfig getRequestConfig();
	
	/**
	 * 功能说明：获取请求客户端对象
	 * AbstractRemoteRequest.getClient();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:45:48
	 * @return
	 */
	protected abstract CloseableHttpClient getClient();
}
