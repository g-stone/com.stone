package com.jmei.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_case_images")
public class CaseImages extends OptionsExts{
	private static final long serialVersionUID = 1L;
	
	private String caseImageId;
	private String caseId;
	private String imageUrl;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "case_image_id", columnDefinition = "varchar(40) comment '案例展示图片表主键ID'")
	public String getCaseImageId() {
		return caseImageId;
	}
	public void setCaseImageId(String caseImageId) {
		this.caseImageId = caseImageId;
	}
	
	@Column(name = "case_id", columnDefinition = "varchar(40) comment '案例ID'")
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Column(name = "image_url", columnDefinition = "varchar(256) comment '图片路径'")
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
