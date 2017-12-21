package com.jmei.manager.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmei.manager.service.ICmsArticleService;

/**
 * 文章控制器
 * @author dba
 */
@RequestMapping("/spring/cms")
@RestController("cmsArticleController")
public class CmsArticleController {
	private ICmsArticleService cmsArticleService;
	
	public ICmsArticleService getCmsArticleService() {
		return cmsArticleService;
	}
	@Resource(name = "cmsArticleService")
	public void setCmsArticleService(ICmsArticleService cmsArticleService) {
		this.cmsArticleService = cmsArticleService;
	}
}
