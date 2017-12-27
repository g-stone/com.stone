package com.jmei.manager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jmei.manager.service.ICmsArticleCategoryService;
import com.jmei.models.entity.ArticleCategory;
import com.stone.data.controller.BasicController;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageQueryModel;
import com.stone.tools.jdbc.PageQuerySupport;
import com.stone.tools.jdbc.PageSupport;
import com.stone.tools.model.ResultObject;

/**
 * 文章栏目分类控制器
 * @author dba
 */
@RequestMapping("/spring/cms/article/category")
@RestController("cmsArticleCategoryController")
public class CmsArticleCategoryController extends BasicController{
	@RequestMapping(value = "/list")
	public ModelAndView caseListView(Model model){
		ModelAndView view = new ModelAndView();
		
		get().info("查询案例...");
		
		view.setViewName("article-category-list");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/page")
	public PageSupport<ArticleCategory> casePage(@RequestBody PageQuerySupport<PageQueryModel> model){
		PageSupport<ArticleCategory> page = new PageSupport<ArticleCategory>();
		page.setCurrentPage(model.getPageIndex());
		page.setSize(model.getPageSize());
		Map<String, CriteriaNameBean> parameter = new HashMap<String, CriteriaNameBean>();
		cmsArticleCategoryService.queryBeanList(
							"select count(*) from t_article_category where 1 = 1 ", 
							"select * from t_article_category where 1 = 1 ", 
							parameter, page, ArticleCategory.class, "order by sort_no");
		return page;
	}
	
	@RequestMapping("/edit")
	public ModelAndView caseEditorView(Model model, HttpServletRequest req){
		ModelAndView view = new ModelAndView();
		
		editPreOption(model, req, ArticleCategory.class);
		
		view.setViewName("article-category-edit");
		return view;
	}
	
	@RequestMapping("/edited")
	public ResultObject caseEdit(@RequestBody ArticleCategory articleCategory, HttpServletRequest req){
		ResultObject result = new ResultObject();
		
		cmsArticleCategoryService.addOrUpdateArticleCategory(articleCategory);
		
		return result;
	}
	
	@RequestMapping("/delete")
	public ResultObject categoryDel(Model model, HttpServletRequest req){
		return delPreOption(req, ArticleCategory.class);
	}
	
	
	private ICmsArticleCategoryService cmsArticleCategoryService;
	public ICmsArticleCategoryService getCmsArticleCategoryService() {
		return cmsArticleCategoryService;
	}
	@Resource(name = "cmsArticleCategoryService")
	public void setCmsArticleCategoryService(ICmsArticleCategoryService cmsArticleCategoryService) {
		this.cmsArticleCategoryService = cmsArticleCategoryService;
	}
}
