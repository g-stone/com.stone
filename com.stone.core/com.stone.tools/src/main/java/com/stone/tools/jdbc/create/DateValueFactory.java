/**
 * DateFactory.java
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午4:41:15
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.create;

import java.util.Date;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午4:41:15
 */
public class DateValueFactory implements ValueFactory{
	@Override
	public Object getValue() {
		return new Date(System.currentTimeMillis());
	}
}
