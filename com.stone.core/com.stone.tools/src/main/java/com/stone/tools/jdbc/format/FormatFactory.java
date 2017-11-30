/**
 * FormatFactory.java
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午4:44:00
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.format;

/**
 * 功能说明
 *
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午4:44:00
 *
 */
public interface FormatFactory {
	Object format(Object[] args);
}
