/**
 * CriteriaQueryParseUtils.java
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午3:07:26
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc.tools;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.annotation.QueryConstraint;
import com.stone.tools.jdbc.annotation.QueryFormat;
import com.stone.tools.jdbc.annotation.QueryTable;

/**
 * 功能说明
 * @author lixinpeng
 * @DATE: 2017年2月20日 @TIME: 下午3:07:26
 */
public class CriteriaQueryParseUtils {
	public static TableMeta parseCriteriaTable(Object obj){
		QueryTable table = obj.getClass().getAnnotation(QueryTable.class);
		if(table != null){
			TableMeta meta = new TableMeta();
			meta.setName(table.table());
			meta.setUnique(table.uniqueName());
			
			return meta;
		}
		
		return null;
	}
	
	public static Map<String, CriteriaNameBean> parseCriteriaConstraint(Object o){
		Map<String, CriteriaNameBean> criterias = new HashMap<String, CriteriaNameBean>();
		Class<?> _oc = o.getClass();
		Method[] ms = _oc.getMethods();
		Arrays.sort(ms, new Comparator<Method>(){
			@Override
			public int compare(Method m1, Method m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});
		
		for(Method m:ms){
			if(m.getAnnotations().length > 0){
				QueryConstraint type = m.getAnnotation(QueryConstraint.class);
				QueryFormat format = m.getAnnotation(QueryFormat.class);
				if(type == null){
					continue;
				}
				Object value = ReflectUtil.getValue(o, m, new Object[]{});
				if(value == null || value.toString().equals("")){
					continue;
				}
				if(format != null){
					value = ReflectUtil.getValue(format.format(), "format", new Class[]{Object[].class}, new Object[]{new Object[]{value, format.org(), format.aim()}});
				}
				
				criterias.put(type.criteriaName(), new CriteriaNameBean(CriteriaNameBean.TYPE.values()[type.criteriaType()], type.fieldName(), type.criteriaName(), value));
			}
		}
		
		return criterias;
	}
	
	public static class TableMeta{
		private String name;
		private String unique;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUnique() {
			return unique;
		}
		public void setUnique(String unique) {
			this.unique = unique;
		}
	}
}
