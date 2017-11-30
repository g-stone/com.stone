/**
 * HisRequestHandler.java
 * @author lixinpeng
 * @DATE: 2017年8月3日 @TIME: 下午3:54:03
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http.handler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.stone.tools.ConverteUtils;
import com.stone.tools.http.Handler;
import com.stone.tools.http.model.RemoteResponseModel;

/**
 * 功能说明：HTTP请求结果解析API
 *
 * @author lixinpeng
 * @param <C>
 * @DATE: 2017年8月3日 @TIME: 下午3:54:03
 */
public class RemoteRequestHandler<C> extends Handler<RemoteResponseModel<C>>{
	private static Logger logger = Logger.getLogger(RemoteRequestHandler.class);
	/**
	 * @param realType
	 */
	public RemoteRequestHandler(Class<C> realType, boolean level) {
		super(realType, level);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RemoteResponseModel<C> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		String result = "";
		RemoteResponseModel<C> model = null;
		HttpEntity entity = response.getEntity();
		result = EntityUtils.toString(entity, "UTF-8");
		if(StringUtils.isBlank(result)){
			return model;
		}
		
		Map<String, Object> responseMap = ConverteUtils.toMap(result);
		model = new RemoteResponseModel<C>();
		
		Object tmpValue = responseMap.get("resultStatus");
		if(StringUtils.isBlank(String.valueOf(tmpValue))){
			model.setResultCode(-1);
			model.setMessage("HIS服务超时或异常,请稍后重试");
		}else{
			model.setResultCode(Integer.parseInt(tmpValue.toString()));
			tmpValue = responseMap.get("info");
			if(tmpValue != null){
				model.setMessage(tmpValue.toString());
			}
		}
		
		if(model.getResultCode() == 1){
			Object data = responseMap.get("data");
			if(data != null){
				Method build;
				try {
					build = realType.getDeclaredMethod("build", new Class[]{Map.class});
					if(level){//list
						List<Map<String, Object>> dataMap = (List<Map<String, Object>>)data;
						List<C> list = new ArrayList<C>();
						for(Map<String, Object> map:dataMap){
							C c = (C) realType.newInstance();
							build.invoke(data, new Object[]{map});
							list.add(c);
						}
						model.setDatas(list);
					}else{//object
						C c = (C) realType.newInstance();
						build.invoke(data, new Object[]{(Map<String, Object>)data});
						model.setData(c);
					}
				} catch (Exception e) {
					logger.error("请求返回内容解析异常--->" + e.getMessage(), e);
				}
			}
		}
		
		logger.debug("dected type: " + realType.getName() + ",level:" + level);
		
		return model;
	}
}
