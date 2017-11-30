/**
 * BeanCollection.java
 * @author lixinpeng
 * @DATE: 2016年3月9日 @TIME: 下午6:16:20
 * Copyright (C) 2016 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能说明：记录转换bean
 * 
 * @author lixinpeng
 * @DATE: 2016年3月9日 @TIME: 下午6:16:20
 */
public class BeanCollection<T> {
	private Map<String, Method> methodInfo;
	private List<T> beanInfos;
	private Class<T> clazz;
	
	public BeanCollection(Class<T> clazz) {
		this.clazz = clazz;
		methodInfo = new HashMap<String, Method>();
		beanInfos = new ArrayList<T>();
		
		Method[] methods = clazz.getMethods();
		String methodName = "";
		
		for (Method method : methods) {
			methodName = method.getName();
			
			if (!methodName.startsWith("set")) {
				continue;
			}
			
			methodName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
			methodInfo.put(methodName, method);
		}
	}
	
	/**
	 * 功能说明：执行bean转换 BeanCollection.transformTuple();
	 * 
	 * @author: lixinpeng
	 * @DATE: 2016年3月22日 @TIME: 下午8:54:54
	 * @param tuple
	 * @param aliases
	 * @return
	 */
	public T transformTuple(Object[] tuple, String[] aliases) {
		T obj = null;
		try {
			obj = (T) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if (obj == null) {
			return obj;
		}
		
		String columnName = "";
		for (int index = 0; index < aliases.length;) {
			columnName = MetaNameConvertUtil.convertName(aliases[index]);
			
			if (methodInfo.containsKey(columnName)) {
				setFieldValue(obj, methodInfo.get(columnName), tuple[index]);
			}
			
			index++;
		}
		
		beanInfos.add(obj);
		
		return obj;
	}
	
	/**
	 * 功能说明：反射设置对象属性的值 BeanCollection.setFieldValue();
	 * 
	 * @author: lixinpeng
	 * @DATE: 2016年3月22日 @TIME: 下午8:55:36
	 * @param obj
	 * @param method
	 * @param value
	 */
	protected void setFieldValue(Object obj, Method method, Object value) {
		try {
			method.invoke(obj, getJavaValue(value));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能说明：数据库类型转换为Java类型 BeanCollection.getJavaValue();
	 * 
	 * @author: lixinpeng
	 * @DATE: 2016年3月22日 @TIME: 下午8:56:39
	 * @param sqlValue
	 * @return
	 * @throws SQLException
	 */
	protected Object getJavaValue(Object sqlValue) throws SQLException {
		Object obj = sqlValue;
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			Blob blob = (Blob) obj;
			obj = blob.getBytes(1, (int) blob.length());
		} else if (obj instanceof Clob) {
			Clob clob = (Clob) obj;
			obj = clob.getSubString(1, (int) clob.length());
		} else if ("java.sql.Timestamp".equals(className) || "oracle.sql.TIMESTAMP".equals(className)
				|| "oracle.sql.TIMESTAMPTZ".equals(className)) {
			obj = new Date(((Timestamp) sqlValue).getTime());
		} else if (className != null && className.startsWith("oracle.sql.DATE")) {
			if ("java.sql.Timestamp".equals(className) || "oracle.sql.TIMESTAMP".equals(className)) {
				obj = new Date(((Timestamp) sqlValue).getTime());
			} else {
				obj = (Date) sqlValue;
			}
		} else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(className)) {
				obj = new Date(((Timestamp) sqlValue).getTime());
			}
		}
		
		return obj;
	}
	
	public List<T> getBeanInfos() {
		return beanInfos;
	}
}
