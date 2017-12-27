package com.jmei.manager.service;

import com.jmei.models.entity.ArticleCategory;
import com.stone.data.service.ICommonService;

public interface ICmsArticleCategoryService extends ICommonService{
	void addOrUpdateArticleCategory(ArticleCategory articleCategory);
}
