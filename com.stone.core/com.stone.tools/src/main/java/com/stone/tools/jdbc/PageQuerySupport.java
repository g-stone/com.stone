package com.stone.tools.jdbc;

public class PageQuerySupport<T> {
	private Integer pageIndex;
	private Integer pageSize;
	private Boolean query;
	private String sord;
	private String sidx;
	private Long nd;
	private T parameter;
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getQuery() {
		return query;
	}
	public void setQuery(Boolean query) {
		this.query = query;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public Long getNd() {
		return nd;
	}
	public void setNd(Long nd) {
		this.nd = nd;
	}
	public T getParameter() {
		return parameter;
	}
	public void setParameter(T parameter) {
		this.parameter = parameter;
	}
}
