/**
 * ResultBeanTransformer.java
 * @author lixinpeng
 * @DATE: 2016年3月9日 @TIME: 下午6:11:53
 * Copyright (C) 2016 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

/**
 * 功能说明：数据库记录转换bean对象转换器
 *
 * @author lixinpeng
 * @DATE: 2016年3月9日 @TIME: 下午6:11:53
 */
public class ResultBeanTransformer<T> implements ResultTransformer{
	private static final long serialVersionUID = 1L;
	private Class<T> clazz;
	private BeanCollection<T> collections;
	
	public ResultBeanTransformer(Class<T> clazz){
		this.clazz = clazz;
		collections = new BeanCollection<T>(this.clazz);
	}
	
	public Object transformTuple(Object[] tuple, String[] aliases) {
		return collections.transformTuple(tuple, aliases);
	}
	
	public List<T> transformList(@SuppressWarnings("rawtypes") List collection) {
		return collections.getBeanInfos();
	}
}
