package com.jmei.manager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jmei.manager.service.ICmsCaseCategoryService;
import com.jmei.models.entity.CaseCategory;
import com.stone.tools.controller.BasicController;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageQueryModel;
import com.stone.tools.jdbc.PageQuerySupport;
import com.stone.tools.jdbc.PageSupport;

/**
 * 案例分类控制器
 * @author dba
 */
@RequestMapping("/spring/cms/article/category")
@RestController("cmsCaseCategoryController")
public class CmsCaseCategoryController extends BasicController{
	@RequestMapping(value = "/list")
	public ModelAndView categoryListView(Model model){
		ModelAndView view = new ModelAndView();
		
		get().info("查询案例分类...");
		
		view.setViewName("case-category-list");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/page")
	public PageSupport<CaseCategory> categoryPage(@RequestBody PageQuerySupport<PageQueryModel> model){
		PageSupport<CaseCategory> page = new PageSupport<CaseCategory>();
		page.setCurrentPage(model.getPageIndex());
		page.setSize(model.getPageSize());
		Map<String, CriteriaNameBean> parameter = new HashMap<String, CriteriaNameBean>();
		this.cmsCaseCategoryService.queryBeanList(
							"select count(*) from t_case_category where 1 = 1 ", 
							"select * from t_case_category where 1 = 1 ", 
							parameter, page, CaseCategory.class, "order by sort_no");
		return page;
	}
	
	private ICmsCaseCategoryService cmsCaseCategoryService;
	
	public ICmsCaseCategoryService getCmsCaseCategoryService() {
		return cmsCaseCategoryService;
	}
	@Resource(name = "cmsCaseCategoryService")
	public void setCmsCaseCategoryService(ICmsCaseCategoryService cmsCaseCategoryService) {
		this.cmsCaseCategoryService = cmsCaseCategoryService;
	}
}
