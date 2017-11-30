/**
 * HttpsRemoteRequest.java
 * @author lixinpeng
 * @DATE: 2017年11月6日 @TIME: 下午10:08:54
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

/**
 * 功能说明：HTTPS请求实现
 *
 * @author lixinpeng
 * @DATE: 2017年11月6日 @TIME: 下午10:08:54
 */
public class HttpsRemoteRequest extends AbstractRemoteRequest{
	private String key;
	private String path;
	private String password;
	private static Logger logger = Logger.getLogger(HttpsRemoteRequest.class);
	
	public static HttpsRemoteRequest getInstance(String key, String path, String password){
		return new HttpsRemoteRequest(key, path, password);
	}
	
	@Override
	protected RequestConfig getRequestConfig(){
		return requestConfig;
	}
	
	private HttpsRemoteRequest(int socketTimeout, int connectTimeout, String key, String path, String password){
		this.key = key;
		this.path = path;
		this.password = password;
		config(socketTimeout, connectTimeout);
	}
	
	private HttpsRemoteRequest(String key, String path, String password){
		this(defaultSocketTimeout, defaultConnectTimeout, key, path, password);
	}
	
	private void config(int socketTimeout, int connectTimeout){
		requestConfig = RequestConfig
				.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout)
				.build();
	}
	
	@Override
	protected CloseableHttpClient getClient() {
		byte[] cacheCert = CertCache.getCacheCert(key, path);
		CloseableHttpClient client = null;
		
		if(cacheCert != null){
			InputStream byteStream = null;
			try {
				KeyStore keyStore = KeyStore.getInstance("PKCS12");
				byteStream = new ByteArrayInputStream(cacheCert);
				keyStore.load(byteStream, password.toCharArray());
				
				// Trust own CA and all self-signed certs
				SSLContext sslcontext = SSLContexts.custom()
						.loadKeyMaterial(keyStore, password.toCharArray())
						.build();
				// Allow TLSv1 protocol only
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
						sslcontext,
						new String[] {"TLSv1"},
						null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
				client = HttpClients.custom()
						.setSSLSocketFactory(sslsf)
						.build();
			} catch (Exception e) {
				logger.error("初始化安全请求异常---->" + e.getMessage(), e);
			} finally {
				if(byteStream != null){
					try{
						byteStream.close();
					}catch(Exception e){
						logger.error("关闭证书字节流异常---->" + e.getMessage(), e);
					}
				}
			}
		}
		
		return client;
	}
}
