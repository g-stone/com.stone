package com.jmei.manager.service;

import com.jmei.models.entity.Article;
import com.stone.data.service.ICommonService;

public interface ICmsArticleService extends ICommonService{
	void addOrUpdateArticle(Article article);
}
