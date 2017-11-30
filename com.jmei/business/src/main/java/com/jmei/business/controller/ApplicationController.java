package com.jmei.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jmei.business.entity.Article;
import com.jmei.business.entity.ArticleCategory;
import com.jmei.business.entity.Case;
import com.jmei.business.entity.CaseCategory;
import com.jmei.business.entity.CaseImages;
import com.jmei.business.entity.SiteImages;
import com.jmei.business.model.ResultObject;
import com.jmei.business.service.ISiteIndexService;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageSupport;

@RestController("applicationController")
public class ApplicationController {
	
	@RequestMapping(value = "/index")
	public ModelAndView site(Model model){
		ModelAndView view = new ModelAndView();
		
		List<Case> cases = siteIndexService.queryByMappingProperty(null, Case.class);
		view.addObject("cases", cases);
		
		List<SiteImages> loopImages = siteIndexService.queryByMappingProperty(null, SiteImages.class);
		view.addObject("loopImages", loopImages);
		
		view.setViewName("index");
		return view;
	}
	
	@RequestMapping(value = "/case/category")
	public ResultObject caseCategory(Model model){
		ResultObject result = new ResultObject();
		List<CaseCategory> cateories = siteIndexService.queryByMappingProperty(null, CaseCategory.class);
		if(cateories == null || cateories.isEmpty()){
			result.setCode(ResultObject.EMPTYDS);
			result.setMsg("无数据！");
		} else {
			result.setCode(ResultObject.SUCCESS);
		}
		
		result.setData(cateories);
		
		return result;
	}
	
	@RequestMapping(value = "/case/detail")
	public ModelAndView detail(Model model, String caseId){
		ModelAndView view = new ModelAndView();
		
		Case cases = siteIndexService.get(Case.class, caseId);
		List<CaseImages> caseImages = siteIndexService.queryByMappingProperty(null, CaseImages.class);
		
		view.addObject("case", cases);
		view.addObject("caseImages", caseImages);
		view.setViewName("detail");
		return view;
	}
	
	@RequestMapping(value = "/case/list")
	public ModelAndView list(Model model){
		ModelAndView view = new ModelAndView();
		
		List<Case> cases = siteIndexService.queryByMappingProperty(null, Case.class);
		view.addObject("cases", cases);
		
		view.setViewName("list");
		return view;
	}
	
	@RequestMapping(value = {"/brief", "/service", "/trends", "/recruit", "/contact"})
	public ModelAndView siteRelative(Model model, String id, Integer vi){
		ModelAndView view = new ModelAndView();
		
		ArticleCategory articleCategory = siteIndexService.get(ArticleCategory.class, id);
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("articleCategoryId", articleCategory.getArticleCategoryId());
		Article article = siteIndexService.getByMappingProperty(parameter, Article.class);
		view.addObject("articleCategory", articleCategory);
		view.addObject("article", article);
		view.addObject("vi", vi);
		
		view.setViewName("explain");
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/case/page")
	public ResultObject pageCase(){
		Map<String, CriteriaNameBean> parameter = new HashMap<String, CriteriaNameBean>();
		PageSupport<Case> page = new PageSupport<Case>();
		page = siteIndexService.queryBeanList("", "", parameter, page, Case.class, "");
		
		ResultObject result = new ResultObject();
		
		return result;
	}
	
	private ISiteIndexService siteIndexService;
	public ISiteIndexService getSiteIndexService() {
		return siteIndexService;
	}
	@Resource(name = "siteIndexService")
	public void setSiteIndexService(ISiteIndexService siteIndexService) {
		this.siteIndexService = siteIndexService;
	}
}
