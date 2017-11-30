/**
 * ReflectUtil.java
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午5:40:57
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.stone.tools.jdbc.annotation.DataTable;
import com.stone.tools.jdbc.annotation.DataType;
import com.stone.tools.jdbc.annotation.UpdateField;
import com.stone.tools.jdbc.annotation.ValuePolicy;
import com.stone.tools.jdbc.annotation.WhereConstraint;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月12日 @TIME: 下午5:40:57
 */
public class ReflectUtil {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getValue(Class _c, String method, Class[] parameterTypes, Object[] args){
		Object val = null;
		Method gm;
		try {
			gm = _c.getDeclaredMethod(method, parameterTypes);
			Object op = _c.newInstance();
			val = gm.invoke(op, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return val;
	}
	
	public static Object getValue(Object o, Method method, Object[] args){
		Object val = null;
		try {
			val = method.invoke(o, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return val;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Enum enumOrdinal(Class<? extends Enum> enumClass, String name){
		Enum em = Enum.valueOf(enumClass, name);
		
		return em;
	}
	
	public static String parseInsertSql(Object o){
		Class<?> _oc = o.getClass();
		DataTable table = _oc.getAnnotation(DataTable.class);
		if(table == null){
			return "";
		}
		Method[] ms = _oc.getDeclaredMethods();
		Arrays.sort(ms, new Comparator<Method>(){
			@Override
			public int compare(Method m1, Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		String ifsql = "insert into " + table.table() + "(";
		String ivsql = "values(";
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				DataType type = m.getAnnotation(DataType.class);
				if(type == null){
					continue;
				}
				
				ifsql += type.name() + ", ";
				ivsql += "?, ";
			}
		}
		
		return ifsql.substring(0, ifsql.length() - 2) + ") " + 
				ivsql.substring(0, ivsql.length() - 2) + ")";
	}
	
	public static String parseUpdateSql(Object o){
		Class<?> _oc = o.getClass();
		DataTable table = _oc.getAnnotation(DataTable.class);
		if(table == null){
			return "";
		}
		Method[] ms = _oc.getDeclaredMethods();
		Arrays.sort(ms, new Comparator<Method>(){
			@Override
			public int compare(Method m1, Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		String ifsql = "update " + table.table() + " set ";
		String whsql = "where 1 = 1 ";
		
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				DataType type = m.getAnnotation(DataType.class);
				WhereConstraint where = m.getAnnotation(WhereConstraint.class);
				if(where != null && where.where()){
					if(type != null){
						whsql += "and " + type.name() + " " + where.operator() + " ? ";
					}
				}
				UpdateField field = m.getAnnotation(UpdateField.class);
				if(field == null || !field.update()){
					continue;
				}
				if(type == null){
					continue;
				}
				
				if(field.operator().equals("")){
					ifsql += type.name() + " = ?, ";
				}else{
					ifsql += type.name() + " = " +  type.name() + " " + field.operator() + " ?, ";
				}
			}
		}
		
		return ifsql.substring(0, ifsql.length() - 2) + " " + whsql;
	}
	
	public static DataMeta parseUpdateMeta(Object o){
		DataMeta meta = new DataMeta();
		Class<?> _oc = o.getClass();
		DataTable table = _oc.getAnnotation(DataTable.class);
		if(table == null){
			return null;
		}
		Method[] ms = _oc.getDeclaredMethods();
		Arrays.sort(ms, new Comparator<Method>(){
			@Override
			public int compare(Method m1, Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		int[] types = new int[1];
		Object[] values = new Object[1];
		List<Object> valueArray = new ArrayList<Object>();
		List<Integer> typeArray = new ArrayList<Integer>();
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				DataType type = m.getAnnotation(DataType.class);
				UpdateField field = m.getAnnotation(UpdateField.class);
				ValuePolicy policy = m.getAnnotation(ValuePolicy.class);
				
				if(field == null || !field.update()){
					continue;
				}
				
				if(type == null){
					continue;
				}
				
				Object value = null;
				if(policy != null){
					value = ReflectUtil.getValue(policy.factory(), "getValue", new Class[]{}, new Object[]{});
				}else{
					value = ReflectUtil.getValue(o, m, new Object[]{});
					if(type.isEnum() && value != null){
						value = ReflectUtil.enumOrdinal(type.enumClass(), value.toString()).ordinal();
					}
				}
				valueArray.add(value);
				typeArray.add(type.type());
			}
		}
		
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				DataType type = m.getAnnotation(DataType.class);
				WhereConstraint where = m.getAnnotation(WhereConstraint.class);
				
				if(where == null || !where.where()){
					continue;
				}
				
				if(type == null){
					continue;
				}
				
				Object value = ReflectUtil.getValue(o, m, new Object[]{});
				if(type.isEnum() && value != null){
					value = ReflectUtil.enumOrdinal(type.enumClass(), value.toString()).ordinal();
				}
				
				valueArray.add(value);
				typeArray.add(type.type());
			}
		}
		
		types = new int[typeArray.size()];
		for(int index = 0; index < typeArray.size(); index ++){
			types[index] = typeArray.get(index);
		}
		values = valueArray.toArray(values);
		meta.setParameter(values);
		meta.setMetaType(types);
		return meta;
	}
	
	public static String parseExistSql(Object o){
		Class<?> _oc = o.getClass();
		DataTable table = _oc.getAnnotation(DataTable.class);
		if(table == null){
			return "";
		}
		Method[] ms = _oc.getDeclaredMethods();
		Arrays.sort(ms, new Comparator<Method>(){
			@Override
			public int compare(Method m1, Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		String whsql = "select count(*) from " + table.table() + " where 1 = 1 ";
		
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				DataType type = m.getAnnotation(DataType.class);
				WhereConstraint where = m.getAnnotation(WhereConstraint.class);
				if(where != null && where.where()){
					Object value = ReflectUtil.getValue(o, m, new Object[]{});
					if(type != null && value != null){
						whsql += "and " + type.name() + " " + where.operator() + " ? ";
					}
				}
			}
		}
		
		return whsql;
	}
	
	public static DataMeta parseExistMeta(Object o){
		DataMeta meta = new DataMeta();
		Class<?> _oc = o.getClass();
		DataTable table = _oc.getAnnotation(DataTable.class);
		if(table == null){
			return null;
		}
		Method[] ms = _oc.getDeclaredMethods();
		Arrays.sort(ms, new Comparator<Method>(){
			@Override
			public int compare(Method m1, Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		int[] types = new int[1];
		Object[] values = new Object[1];
		List<Object> valueArray = new ArrayList<Object>();
		List<Integer> typeArray = new ArrayList<Integer>();
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				DataType type = m.getAnnotation(DataType.class);
				WhereConstraint where = m.getAnnotation(WhereConstraint.class);
				
				if(where == null || !where.where()){
					continue;
				}
				
				if(type == null){
					continue;
				}
				
				Object value = ReflectUtil.getValue(o, m, new Object[]{});
				if(type.isEnum() && value != null){
					value = ReflectUtil.enumOrdinal(type.enumClass(), value.toString()).ordinal();
				}
				
				if(value != null){
					valueArray.add(value);
					typeArray.add(type.type());
				}
			}
		}
		
		if(!typeArray.isEmpty()){
			types = new int[typeArray.size()];
			for(int index = 0; index < typeArray.size(); index ++){
				types[index] = typeArray.get(index);
			}
			values = valueArray.toArray(values);
			meta.setParameter(values);
			meta.setMetaType(types);
		}
		return meta;
	}
	
	public static String parseDeleteSql(Object o){
		String whsql = parseExistSql(o);
		
		return "delete" + whsql.substring(whsql.indexOf("from") - 1);
	}
	
	public static DataMeta parseDeleteMeta(Object o){
		return parseExistMeta(o);
	}
	
	public static DataMeta parseDataMeta(Object o){
		DataMeta meta = new DataMeta();
		Class<?> _oc = o.getClass();
		DataTable table = _oc.getAnnotation(DataTable.class);
		if(table == null){
			return null;
		}
		Method[] ms = _oc.getDeclaredMethods();
		Arrays.sort(ms, new Comparator<Method>(){
			@Override
			public int compare(Method m1, Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		int[] types = new int[1];
		Object[] values = new Object[1];
		List<Object> valueArray = new ArrayList<Object>();
		List<Integer> typeArray = new ArrayList<Integer>();
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				DataType type = m.getAnnotation(DataType.class);
				ValuePolicy policy = m.getAnnotation(ValuePolicy.class);
				if(type == null){
					continue;
				}
				
				Object value = null;
				if(policy != null){
					value = ReflectUtil.getValue(policy.factory(), "getValue", new Class[]{}, new Object[]{});
				}else{
					value = ReflectUtil.getValue(o, m, new Object[]{});
					if(type.isEnum() && value != null){
						value = ReflectUtil.enumOrdinal(type.enumClass(), value.toString()).ordinal();
					}
				}
				valueArray.add(value);
				typeArray.add(type.type());
			}
		}
		
		types = new int[typeArray.size()];
		for(int index = 0; index < typeArray.size(); index ++){
			types[index] = typeArray.get(index);
		}
		values = valueArray.toArray(values);
		meta.setParameter(values);
		meta.setMetaType(types);
		return meta;
	}
	
	public static class DataMeta{
		private Object[] parameter;
		private int[] metaType;
		
		public DataMeta(){
			parameter = new Object[]{};
			metaType = new int[]{};
		}
		
		public Object[] getParameter() {
			return parameter;
		}
		public void setParameter(Object[] parameter) {
			this.parameter = parameter;
		}
		public int[] getMetaType() {
			return metaType;
		}
		public void setMetaType(int[] metaType) {
			this.metaType = metaType;
		}
		
		@Override
		public String toString() {
			return "DataMeta [\n\bparameter:\n\t" + Arrays.toString(parameter) + ", \n\bmetaType:\n\t" + Arrays.toString(metaType)
					+ "]";
		}
	}
}
