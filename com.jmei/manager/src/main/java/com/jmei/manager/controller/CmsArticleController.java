package com.jmei.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jmei.manager.model.ArticleModel;
import com.jmei.manager.service.ICmsArticleService;
import com.jmei.models.entity.Article;
import com.jmei.models.entity.ArticleCategory;
import com.stone.data.controller.BasicController;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageQueryModel;
import com.stone.tools.jdbc.PageQuerySupport;
import com.stone.tools.jdbc.PageSupport;
import com.stone.tools.model.ResultObject;

/**
 * 文章控制器
 * @author dba
 */
@RequestMapping("/spring/cms/article")
@RestController("cmsArticleController")
public class CmsArticleController extends BasicController{
	@RequestMapping(value = "/list")
	public ModelAndView caseListView(Model model){
		ModelAndView view = new ModelAndView();
		
		get().info("查询案例...");
		
		view.setViewName("article-list");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/page")
	public PageSupport<ArticleModel> casePage(@RequestBody PageQuerySupport<PageQueryModel> model){
		PageSupport<ArticleModel> page = new PageSupport<ArticleModel>();
		page.setCurrentPage(model.getPageIndex());
		page.setSize(model.getPageSize());
		Map<String, CriteriaNameBean> parameter = new HashMap<String, CriteriaNameBean>();
		cmsArticleService.queryBeanList(
							"select count(*) from t_article where 1 = 1 ", 
							"select a.article_id, a.article_category_id, a.article_title, c.category_name, a.is_show, a.update_date "
							+ "from t_article a left join t_article_category c on a.article_category_id = c.article_category_id "
							+ "where 1 = 1 ", 
							parameter, page, ArticleModel.class, "order by a.sort_no");
		return page;
	}
	
	@RequestMapping("/edit")
	public ModelAndView caseEditorView(Model model, HttpServletRequest req){
		ModelAndView view = new ModelAndView();
		
		editPreOption(model, req, Article.class);
		
		List<ArticleCategory> categories = cmsArticleService.queryByMappingProperty(null, ArticleCategory.class);
		model.addAttribute("categories", categories);
		
		view.setViewName("article-edit");
		return view;
	}
	
	@RequestMapping("/edited")
	public ResultObject caseEdit(@RequestBody Article article, HttpServletRequest req){
		ResultObject result = new ResultObject();
		
		cmsArticleService.addOrUpdateArticle(article);
		
		return result;
	}
	
	@RequestMapping("/delete")
	public ResultObject categoryDel(Model model, HttpServletRequest req){
		return delPreOption(req, Article.class);
	}
	
	
	private ICmsArticleService cmsArticleService;
	
	public ICmsArticleService getCmsArticleService() {
		return cmsArticleService;
	}
	@Resource(name = "cmsArticleService")
	public void setCmsArticleService(ICmsArticleService cmsArticleService) {
		this.cmsArticleService = cmsArticleService;
	}
}
