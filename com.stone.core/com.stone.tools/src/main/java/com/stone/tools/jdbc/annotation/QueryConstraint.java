/**
 * QueryConstraint.java
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午3:04:11
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.stone.tools.jdbc.CriteriaNameBean;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午3:04:11
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface QueryConstraint {
	String uniqueName() default "";
	String criteriaName();
	String fieldName();
	int criteriaType() default CriteriaNameBean.C_EQ;
}
