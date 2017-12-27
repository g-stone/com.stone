package com.jmei.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jmei.manager.service.ICmsArticleService;
import com.jmei.models.entity.Article;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsArticleService")
public class CmsArticleServiceImpl extends CommonServiceImpl implements ICommonService, ICmsArticleService{

	@Override
	public void addOrUpdateArticle(Article article) {
		Date curDate = new Date(System.currentTimeMillis());
		article.setUpdateDate(curDate);
		
		if(StringUtils.hasText(article.getArticleId())){
			update(article);
		} else {
			article.setCreateDate(curDate);
			add(article);
		}
	}

}
