package com.jmei.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_case")
public class Case extends OptionsExts{
	private static final long serialVersionUID = 1L;
	
	private String caseId;
	private String caseCategoryId;
	private String industryCategoryId;
	private String caseName;
	private String employ;
	private String caseOptionImage;
	private String shortDesc;
	private String desc;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "case_id")
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Column(name = "case_category_id")
	public String getCaseCategoryId() {
		return caseCategoryId;
	}
	public void setCaseCategoryId(String caseCategoryId) {
		this.caseCategoryId = caseCategoryId;
	}
	
	@Column(name = "industry_category_id")
	public String getIndustryCategoryId() {
		return industryCategoryId;
	}
	public void setIndustryCategoryId(String industryCategoryId) {
		this.industryCategoryId = industryCategoryId;
	}
	
	@Column(name = "case_name")
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	@Column(name = "employ")
	public String getEmploy() {
		return employ;
	}
	public void setEmploy(String employ) {
		this.employ = employ;
	}
	
	@Column(name = "case_option_image")
	public String getCaseOptionImage() {
		return caseOptionImage;
	}
	public void setCaseOptionImage(String caseOptionImage) {
		this.caseOptionImage = caseOptionImage;
	}
	
	@Column(name = "short_desc")
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	@Column(name = "desc")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
