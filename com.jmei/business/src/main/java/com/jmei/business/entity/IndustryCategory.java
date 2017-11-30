package com.jmei.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_industry_category")
public class IndustryCategory extends OptionsExts{
	private static final long serialVersionUID = 1L;
	
	private String industryCategoryId;
	private String categoryName;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "industry_category_id")
	public String getIndustryCategoryId() {
		return industryCategoryId;
	}
	public void setIndustryCategoryId(String industryCategoryId) {
		this.industryCategoryId = industryCategoryId;
	}
	@Column(name = "category_name")
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
