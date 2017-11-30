/**
 * ValuePolicy.java
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午4:32:07
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
 * @DATE: 2017年2月12日 @TIME: 下午4:32:07
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ValuePolicy {
	Class<?> factory() default Object.class;
}
