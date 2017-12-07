package com.jmei.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_article")
public class Article extends OptionsExts{
	private static final long serialVersionUID = 1L;
	
	private String articleId;
	private String articleCategoryId;
	private String articleTitle;
	private String contents;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "article_id")
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	@Column(name = "article_category_id")
	public String getArticleCategoryId() {
		return articleCategoryId;
	}
	public void setArticleCategoryId(String articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}
	
	@Column(name = "article_title")
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	
	@Column(name = "contents")
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
}
