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

import com.jmei.manager.service.ICmsCaseService;
import com.jmei.models.entity.Case;
import com.stone.data.controller.BasicController;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageQueryModel;
import com.stone.tools.jdbc.PageQuerySupport;
import com.stone.tools.jdbc.PageSupport;

/**
 * 案例控制器
 * @author dba
 */
@RequestMapping("/spring/cms/case")
@RestController("cmsCaseController")
public class CmsCaseController extends BasicController{
	@RequestMapping(value = "/list")
	public ModelAndView industryListView(Model model){
		ModelAndView view = new ModelAndView();
		
		get().info("查询案例...");
		
		view.setViewName("case-list");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/page")
	public PageSupport<Case> industryPage(@RequestBody PageQuerySupport<PageQueryModel> model){
		PageSupport<Case> page = new PageSupport<Case>();
		page.setCurrentPage(model.getPageIndex());
		page.setSize(model.getPageSize());
		Map<String, CriteriaNameBean> parameter = new HashMap<String, CriteriaNameBean>();
		cmsCaseService.queryBeanList(
							"select count(*) from t_case where 1 = 1 ", 
							"select * from t_case where 1 = 1 ", 
							parameter, page, Case.class, "order by sort_no");
		return page;
	}
	
	private ICmsCaseService cmsCaseService;
	public ICmsCaseService getCmsCaseService() {
		return cmsCaseService;
	}
	@Resource(name = "cmsCaseService")
	public void setCmsCaseService(ICmsCaseService cmsCaseService) {
		this.cmsCaseService = cmsCaseService;
	}
}
