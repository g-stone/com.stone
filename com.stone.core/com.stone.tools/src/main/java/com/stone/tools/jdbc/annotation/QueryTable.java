/**
 * QueryTable.java
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午3:51:26
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
 * @DATE: 2017年2月20日 @TIME: 下午3:51:26
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface QueryTable {
	String table();
	String uniqueName() default "";
}
