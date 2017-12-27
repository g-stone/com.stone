package com.jmei.manager.model;

import com.jmei.models.entity.Case;

public class CaseModel extends Case{
	private static final long serialVersionUID = 1L;
	
	private String categoryName;
	private String industryName;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
}
