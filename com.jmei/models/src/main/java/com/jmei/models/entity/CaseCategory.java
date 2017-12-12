package com.jmei.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_case_category")
public class CaseCategory extends OptionsExts{
	private static final long serialVersionUID = 1L;
	
	private String caseCategoryId;
	private String categoryName;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "case_category_id", columnDefinition = "varchar(40) comment '案例分类表主键ID'")
	public String getCaseCategoryId() {
		return caseCategoryId;
	}
	public void setCaseCategoryId(String caseCategoryId) {
		this.caseCategoryId = caseCategoryId;
	}
	
	@Column(name = "category_name", columnDefinition = "varchar(256) comment '案例分类名称'")
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
