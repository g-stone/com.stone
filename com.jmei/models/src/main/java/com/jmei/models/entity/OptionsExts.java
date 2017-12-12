package com.jmei.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class OptionsExts implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected Integer isShow;
	protected Integer sortNo;
	protected Date createDate;
	protected Date updateDate;
	
	@Column(name = "is_show", columnDefinition = "int comment '是否展示 0 否 1 是'")
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	@Column(name = "sort_no", columnDefinition = "int comment '排序号'")
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	
	@Column(name = "create_date", columnDefinition = "datetime comment '创建时间'")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "update_date", columnDefinition = "datetime comment '更新时间'")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
