/**
 * WhereConstraint.java
 * @author lixinpeng
 * @DATE: 2017年2月28日 @TIME: 上午11:20:40
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月28日 @TIME: 上午11:20:40
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface WhereConstraint {
	boolean where() default true;
	String operator() default "=";
}
