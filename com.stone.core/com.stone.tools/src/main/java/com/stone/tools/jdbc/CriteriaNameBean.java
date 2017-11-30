/**
 * CriteriaBean.java
 * @author lixinpeng
 * @DATE: 2016年3月22日 @TIME: 下午4:11:43
 * Copyright (C) 2016 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc;

/**
 * 功能说明：占位符HSQL辅助类
 * @author lixinpeng
 * @DATE: 2016年3月22日 @TIME: 下午4:11:43
 */
public class CriteriaNameBean {
	private TYPE criteriaType;
	private String criteriaName;
	private String placeholderName;
	private Object criteriaValue;
	private Object defaultCriteriaValue;
	
	public CriteriaNameBean(TYPE criteriaType, String criteriaName, String placeholderName, Object criteriaValue, Object defaultCriteriaValue){
		this.criteriaName = criteriaName;
		this.criteriaType = criteriaType;
		this.placeholderName = placeholderName;
		this.criteriaValue = criteriaValue;
		this.defaultCriteriaValue = defaultCriteriaValue;
	}
	
	public CriteriaNameBean(TYPE criteriaType, String criteriaName, String placeholderName, Object criteriaValue){
		this(criteriaType, criteriaName, placeholderName, criteriaValue, null);
	}
	
	public TYPE getCriteriaType() {
		return criteriaType;
	}
	public void setCriteriaType(TYPE criteriaType) {
		this.criteriaType = criteriaType;
	}
	public String getCriteriaName() {
		return criteriaName;
	}
	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}
	public String getPlaceholderName() {
		return placeholderName;
	}
	public void setPlaceholderName(String placeholderName) {
		this.placeholderName = placeholderName;
	}
	/**
	 * 功能说明：条件值：获取判断条件值，当为字符串时为空返回null；其它情况返回对应设置的值
	 * CriteriaNameBean.getCriteriaValue();
	 * @author: lixinpeng
	 * @DATE: 2016年3月22日  @TIME: 下午8:44:08
	 * @return
	 */
	public Object getCriteriaValue() {
		Object tmpValue = null;
		if(criteriaValue != null){
			tmpValue = criteriaValue;
		}else if(defaultCriteriaValue != null){
			tmpValue = defaultCriteriaValue;
		}
		
		if(tmpValue instanceof CharSequence){
			if(!tmpValue.toString().trim().equals("")){
				if(getCriteriaType().equals(TYPE.LIKE)){
					return "%" + tmpValue + "%";
				}else{
					return tmpValue;
				}
			}else{
				return null;
			}
		}else{
			return tmpValue;
		}
	}
	public void setCriteriaValue(Object criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	/**
	 * 功能说明：判断该条件是否附加到where条件后面
	 * CriteriaNameBean.isApplyCriteria();
	 * @author: lixinpeng
	 * @DATE: 2016年3月22日  @TIME: 下午8:43:45
	 * @return
	 */
	public boolean isApplyCriteria(){
		Object cv = getCriteriaValue();
		if(cv == null){
			return false;
		}
		
		if(cv instanceof CharSequence){
			if(cv.toString().trim().equals("")){
				return false;
			}else{
				return true;
			}
		}
		
		return true;
	}
	/**
	 * 功能说明：返回条件字符串
	 * CriteriaNameBean.criteriaString();
	 * @author: lixinpeng
	 * @DATE: 2016年3月22日  @TIME: 下午8:42:55
	 * @return
	 */
	public String criteriaString(){
		String str = "";
		if(isApplyCriteria()){
			switch(getCriteriaType()){
			case EGT:
				str += " and " + getCriteriaName() + " >= :" + getPlaceholderName() + " ";
				break;
			case ELT:
				str += " and " + getCriteriaName() + " <= :" + getPlaceholderName() + " ";
				break;
			case EQ:
				str += " and " + getCriteriaName() + " = :" + getPlaceholderName() + " ";
				break;
			case GT:
				str += " and " + getCriteriaName() + " > :" + getPlaceholderName() + " ";
				break;
			case IN:
				str += " and " + getCriteriaName() + " in (:" + getPlaceholderName() + ") ";
				break;
			case LIKE:
				str += " and " + getCriteriaName() + " like :" + getPlaceholderName() + " ";
				break;
			case LT:
				str += " and " + getCriteriaName() + " < :" + getPlaceholderName() + " ";
				break;
			default:
				break;
			}
		}
		
		return str;
	}
	
	@Override
	public String toString() {
		return "CriteriaNameBean [criteriaType=" + criteriaType + ", criteriaName=" + criteriaName
				+ ", placeholderName=" + placeholderName + ", criteriaValue=" + criteriaValue
				+ ", defaultCriteriaValue=" + defaultCriteriaValue + "]";
	}
	/**
	 * 功能说明:条件类型EQ(=)，GT(>)，EGT(>=)，LT(<)，ELT(<=)，LIKE，IN(参数是List)
	 * @author lixinpeng
	 * @DATE: 2016年3月22日 @TIME: 下午8:45:05
	 */
	public static enum TYPE {EQ, GT, EGT, LT, ELT, LIKE, IN};
	
	public final static int C_EQ = 0;
	public final static int C_GT = 1;
	public final static int C_EGT = 2;
	public final static int C_LT = 3;
	public final static int C_ELT = 4;
	public final static int C_LIKE = 5;
	public final static int C_IN = 6;
}
