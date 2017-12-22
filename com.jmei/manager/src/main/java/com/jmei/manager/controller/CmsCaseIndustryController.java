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

import com.jmei.manager.service.ICmsCaseIndustryService;
import com.jmei.models.entity.IndustryCategory;
import com.stone.tools.controller.BasicController;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageQueryModel;
import com.stone.tools.jdbc.PageQuerySupport;
import com.stone.tools.jdbc.PageSupport;

/**
 * 案例行业分类控制器
 * @author dba
 *
 */
@RequestMapping("/spring/cms/case/industry")
@RestController("cmsCaseIndustryController")
public class CmsCaseIndustryController extends BasicController{
	@RequestMapping(value = "/list")
	public ModelAndView industryListView(Model model){
		ModelAndView view = new ModelAndView();
		
		get().info("查询行业分类...");
		
		view.setViewName("case-industry-list");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/page")
	public PageSupport<IndustryCategory> industryPage(@RequestBody PageQuerySupport<PageQueryModel> model){
		PageSupport<IndustryCategory> page = new PageSupport<IndustryCategory>();
		page.setCurrentPage(model.getPageIndex());
		page.setSize(model.getPageSize());
		Map<String, CriteriaNameBean> parameter = new HashMap<String, CriteriaNameBean>();
		cmsCaseIndustryService.queryBeanList(
							"select count(*) from t_industry_category where 1 = 1 ", 
							"select * from t_industry_category where 1 = 1 ", 
							parameter, page, IndustryCategory.class, "order by sort_no");
		return page;
	}
	
	private ICmsCaseIndustryService cmsCaseIndustryService;
	public ICmsCaseIndustryService getCmsCaseIndustryService() {
		return cmsCaseIndustryService;
	}
	@Resource(name = "cmsCaseIndustryService")
	public void setCmsCaseIndustryService(ICmsCaseIndustryService cmsCaseIndustryService) {
		this.cmsCaseIndustryService = cmsCaseIndustryService;
	}
}
