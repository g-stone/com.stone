package com.jmei.manager.model;

import com.jmei.models.entity.Article;

public class ArticleModel extends Article{
	private static final long serialVersionUID = 1L;
	
	private String categoryName;
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
