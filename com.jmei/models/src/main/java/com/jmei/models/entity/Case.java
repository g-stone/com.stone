package com.jmei.models.entity;

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
	private String caseDesc;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "case_id", columnDefinition = "varchar(40) comment '案例表主键ID'")
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Column(name = "case_category_id", columnDefinition = "varchar(40) comment '案例所属分类表主键ID'")
	public String getCaseCategoryId() {
		return caseCategoryId;
	}
	public void setCaseCategoryId(String caseCategoryId) {
		this.caseCategoryId = caseCategoryId;
	}
	
	@Column(name = "industry_category_id", columnDefinition = "varchar(40) comment '案例所属行业表主键ID'")
	public String getIndustryCategoryId() {
		return industryCategoryId;
	}
	public void setIndustryCategoryId(String industryCategoryId) {
		this.industryCategoryId = industryCategoryId;
	}
	
	@Column(name = "case_name", columnDefinition = "varchar(256) comment '案例名称'")
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	@Column(name = "employ", columnDefinition = "varchar(128)comment '合作伙伴名'")
	public String getEmploy() {
		return employ;
	}
	public void setEmploy(String employ) {
		this.employ = employ;
	}
	
	@Column(name = "case_option_image", columnDefinition = "varchar(256) comment '案例展示缩图'")
	public String getCaseOptionImage() {
		return caseOptionImage;
	}
	public void setCaseOptionImage(String caseOptionImage) {
		this.caseOptionImage = caseOptionImage;
	}
	
	@Column(name = "short_desc", columnDefinition = "varchar(1024) comment '案例简述'")
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	@Column(name = "case_desc", columnDefinition = "longtext comment '案例描述'")
	public String getCaseDesc() {
		return caseDesc;
	}
	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}
}
