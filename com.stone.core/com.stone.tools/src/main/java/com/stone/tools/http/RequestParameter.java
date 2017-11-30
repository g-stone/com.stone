/**
 * RequestParameter.java
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:45:11
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 功能说明：远程请求参数对象
 *
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午1:45:11
 */
public class RequestParameter {
	public final static String REQUEST_URL = "request_url";
	public final static String REQUEST_TYPE = "request_type";
	public final static String RESPONSE_TYPE = "response_type";
	public final static String RESPONSE_CHARSET = "response_charset";
	
	private Map<String, Object> parameter = new HashMap<String, Object>();
	
	public void addParameter(String name, Object value){
		parameter.put(name, value);
	}
	public void addParameter(Map<String, Object> param){
		parameter.putAll(param);
	}
	
	public String getRequestUrl(){
		if(parameter.containsKey(REQUEST_URL)){
			return String.valueOf(parameter.get(REQUEST_URL));
		}else{
			return null;
		}
	}
	
	public String getRequestType(){
		if(parameter.containsKey(REQUEST_TYPE)){
			return String.valueOf(parameter.get(REQUEST_TYPE));
		}else{
			return null;
		}
	}
	
	public String getResponseType(){
		if(parameter.containsKey(RESPONSE_TYPE)){
			return String.valueOf(parameter.get(RESPONSE_TYPE));
		}else{
			return null;
		}
	}
	
	public String getResponseCharset(){
		if(parameter.containsKey(RESPONSE_CHARSET)){
			return String.valueOf(parameter.get(RESPONSE_CHARSET));
		}else{
			return null;
		}
	}
	
	public Map<String, Object> getRequestParameters(){
		Map<String, Object> requestParameter = new HashMap<String, Object>();
		for(Entry<String, Object> entry:parameter.entrySet()){
			if(entry.getKey().equals(REQUEST_TYPE) || 
					entry.getKey().equals(REQUEST_URL) || 
					entry.getKey().equals(RESPONSE_TYPE) || 
					entry.getKey().equals(RESPONSE_CHARSET)){
				continue;
			}
			
			requestParameter.put(entry.getKey(), entry.getValue());
		}
		
		return requestParameter;
	}
}
