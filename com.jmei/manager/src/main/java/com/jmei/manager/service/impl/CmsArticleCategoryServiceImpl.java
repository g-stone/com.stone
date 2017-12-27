package com.jmei.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jmei.manager.service.ICmsArticleCategoryService;
import com.jmei.models.entity.ArticleCategory;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsArticleCategoryService")
public class CmsArticleCategoryServiceImpl extends CommonServiceImpl implements ICommonService, ICmsArticleCategoryService{

	@Override
	public void addOrUpdateArticleCategory(ArticleCategory articleCategory) {
		Date curDate = new Date(System.currentTimeMillis());
		articleCategory.setUpdateDate(curDate);
		
		if(StringUtils.hasText(articleCategory.getArticleCategoryId())){
			update(articleCategory);
		} else {
			articleCategory.setCreateDate(curDate);
			add(articleCategory);
		}
	}
}
