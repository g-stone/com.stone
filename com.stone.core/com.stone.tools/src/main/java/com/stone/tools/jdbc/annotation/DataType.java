/**
 * DataType.java
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午3:49:25
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
 * @DATE: 2017年2月12日 @TIME: 下午3:49:25
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface DataType {
	int type() default 12;//默认varchar类型
	boolean isEnum() default false;
	@SuppressWarnings("rawtypes")
	Class<? extends Enum> enumClass() default Enum.class;
	String name();
}
