package com.jmei.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_site_images")
public class SiteImages extends OptionsExts{
	private static final long serialVersionUID = 1L;
	
	private String imageId;
	private Integer imageType;
	private String imageUrl;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "image_id", columnDefinition = "varchar(40) comment '图片表主键ID'")
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@Column(name = "image_type", columnDefinition = "int comment '图片分类'")
	public Integer getImageType() {
		return imageType;
	}
	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}
	
	@Column(name = "image_url", columnDefinition = "varchar(256) comment '图片地址'")
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
