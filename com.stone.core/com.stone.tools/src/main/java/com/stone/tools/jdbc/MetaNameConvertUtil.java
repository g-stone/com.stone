/**
 * MetaNameConvertUtil.java
 * @author lixinpeng
 * @DATE: 2016年2月26日 @TIME: 下午4:54:37
 * Copyright (C) 2016 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能说明:字段名格式化工具
 *
 * @author lixinpeng
 * @DATE: 2016年2月26日 @TIME: 下午4:54:37
 *
 */
public class MetaNameConvertUtil {
	private final static String CONVERT_REGX = "_([a-z]{1})";
	private final static Pattern PATTERN = Pattern.compile(CONVERT_REGX);
	
	public static String convertName(String aName){
		String tmpConvertedName = "";
		
		tmpConvertedName = aName.toLowerCase();
		Matcher tmpMatcher = PATTERN.matcher(tmpConvertedName);
		StringBuffer tmpAppender = new StringBuffer();
		while(tmpMatcher.find()){
			tmpMatcher.appendReplacement(tmpAppender, tmpMatcher.group(1).toUpperCase());
		}
		
		tmpMatcher.appendTail(tmpAppender);
		
		return tmpAppender.toString();
	}
}
