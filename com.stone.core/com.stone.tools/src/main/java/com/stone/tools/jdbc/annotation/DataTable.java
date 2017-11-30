/**
 * Table.java
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午7:01:53
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午7:01:53
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface DataTable {
	String table();
}
