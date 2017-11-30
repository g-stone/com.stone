/**
 * CertCache.java
 * @author lixinpeng
 * @DATE: 2017年11月6日 @TIME: 下午10:22:00
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.http;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 功能说明：证书缓存
 *
 * @author lixinpeng
 * @DATE: 2017年11月6日 @TIME: 下午10:22:00
 */
public class CertCache {
	private static Logger logger = Logger.getLogger(CertCache.class);
	private static Map<String, byte[]> certs = new HashMap<String, byte[]>();
	
	/**
	 * 功能说明：从路径中加载证书内容
	 * CertCache.loadCert();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:16:32
	 * @param path 证书路径
	 * @return
	 */
	public static byte[] loadCert(String path){
		byte[] byteCert = null;
		File srcFile = new File(FilenameUtils.separatorsToSystem(path));
		
		if(srcFile.exists() && srcFile.isFile()){
			FileInputStream instream = null;
			try {
				instream = new FileInputStream(srcFile);
				byteCert = IOUtils.toByteArray(instream);
			} catch (Exception e) {
				logger.error("获取解析证书文件输入流异常--->" + e.getMessage(), e);
			} finally {
				if(instream != null){
					try{
						instream.close();
					}catch(Exception e){
						logger.error("关闭证书文件输入流异常--->" + e.getMessage(), e);
					}
				}
			}
		}
		
		return byteCert;
	}
	
	/**
	 * 功能说明：获取缓存的证书内容
	 * CertCache.getCacheCert();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:17:12
	 * @param key 证书对应的KEY
	 * @param path 证书路径
	 * @return
	 */
	public static byte[] getCacheCert(String key, String path){
		if(certs.containsKey(key)){
			return certs.get(key);
		}
		
		byte[] byteCert = null;
		
		if(path != null && StringUtils.isNotBlank(path.toString())){
			byteCert = loadCert(path.toString());
		}
		
		if(byteCert != null){
			certs.put(key, byteCert);
		}
		
		return byteCert;
	}
}
