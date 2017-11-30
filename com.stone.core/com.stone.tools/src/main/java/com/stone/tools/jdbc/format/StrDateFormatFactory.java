/**
 * DateFormatFactory.java
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午4:43:34
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.format;

import java.text.ParseException;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午4:43:34
 */
public class StrDateFormatFactory implements FormatFactory{
	private final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public Object format(Object[] args) {
		String ds = "";
		String pattern = DEFAULT_PATTERN;
		String aimPattern = DEFAULT_PATTERN;
		if(args != null && args.length > 0){
			if(args[0] != null){
				if(args.length > 2){
					pattern = args[1].toString();
					aimPattern = args[2].toString();
				}
				try {
					ds = DateFormatUtils.format(DateUtils.parseDate(args[0].toString(), new String[]{pattern}), aimPattern);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		return ds;
	}

}
