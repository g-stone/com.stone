/**
 * Paging.java
 * @author:dba
 * @DATE: 2015-10-20 @TIME: 上午11:32:00
 * Copyright (C) 2015 西安上达信息科技有限公司
 */
package com.stone.tools.jdbc;

import java.util.List;

/**
 * 功能说明: 分页显示
 * @author: dba
 * @DATE: 2015-10-20 @TIME: 上午11:32:00
 */
public class PageSupport<T> {
	private int rows; // 数据总条数
	private int size; // 每页显示多少条数据
	private int pages; // 显示多少页
	private int currentPage = 1; // 当前页
	
	private int firstFetchIndex;
	private int lastFetchIndex;
	private List<T> beanList;
	
	private boolean all;
	
	public PageSupport(){
		all = false;
		size = 15;
	}
	
	public List<T> getBeanList() {
		return (List<T>) beanList;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int getFirstFetchIndex() {
		return firstFetchIndex;
	}
	public int getLastFetchIndex() {
		return lastFetchIndex;
	}
	public int getPages() {
		return pages;
	}
	public int getRows() {
		return rows;
	}
	public int getSize() {
		return size;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	private void setFirstFetchIndex() {
		if(rows > 1){
			firstFetchIndex = 0;
		}
		
		firstFetchIndex = (getCurrentPage() - 1) * getSize();
	}
	private void setLastFetchIndex() {
		if(rows > 1){
			lastFetchIndex = 0;
		}
		
		lastFetchIndex = getCurrentPage() * getSize();
		
		if(lastFetchIndex > rows){
			lastFetchIndex = rows;
		}
	}
	private void setPages() {
		if (rows % size == 0) {
			pages = rows / size;
		} else {
			pages = rows / size + 1;
		}
	}
	public void setRows(int rows) {
		this.rows = rows;
		setFirstFetchIndex();
		setLastFetchIndex();
		setPages();
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isAll() {
		return all;
	}
	public void setAll(boolean all) {
		this.all = all;
	}
	
	@Override
	public String toString() {
		return "PageSupport [rows=" + rows + ", size=" + size + ", pages=" + pages + ", currentPage=" + currentPage
				+ ", firstFetchIndex=" + firstFetchIndex + ", lastFetchIndex=" + lastFetchIndex + "]";
	}
	
}
