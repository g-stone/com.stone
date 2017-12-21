package com.jmei.manager.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmei.manager.service.ICmsArticleCategoryService;

/**
 * 文章栏目分类控制器
 * @author dba
 */
@RequestMapping("/spring/cms")
@RestController("cmsArticleCategoryController")
public class CmsArticleCategoryController {
	private ICmsArticleCategoryService cmsArticleCategoryService;
	
	public ICmsArticleCategoryService getCmsArticleCategoryService() {
		return cmsArticleCategoryService;
	}
	@Resource(name = "cmsArticleCategoryService")
	public void setCmsArticleCategoryService(ICmsArticleCategoryService cmsArticleCategoryService) {
		this.cmsArticleCategoryService = cmsArticleCategoryService;
	}
}
