/**
 * UuidFactory.java
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午4:40:51
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.create;

import java.util.UUID;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午4:40:51
 */
public class UuidValueFactory implements ValueFactory{
	@Override
	public Object getValue() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
