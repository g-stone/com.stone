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

import com.jmei.manager.model.CaseModel;
import com.jmei.manager.service.ICmsCaseService;
import com.jmei.models.entity.Case;
import com.jmei.models.entity.CaseCategory;
import com.jmei.models.entity.IndustryCategory;
import com.stone.data.controller.BasicController;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageQueryModel;
import com.stone.tools.jdbc.PageQuerySupport;
import com.stone.tools.jdbc.PageSupport;
import com.stone.tools.model.ResultObject;

/**
 * 案例控制器
 * @author dba
 */
@RequestMapping("/spring/cms/case")
@RestController("cmsCaseController")
public class CmsCaseController extends BasicController{
	@RequestMapping(value = "/list")
	public ModelAndView caseListView(Model model){
		ModelAndView view = new ModelAndView();
		
		get().info("查询案例...");
		
		view.setViewName("case-list");
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/page")
	public PageSupport<CaseModel> casePage(@RequestBody PageQuerySupport<PageQueryModel> model){
		PageSupport<CaseModel> page = new PageSupport<CaseModel>();
		page.setCurrentPage(model.getPageIndex());
		page.setSize(model.getPageSize());
		Map<String, CriteriaNameBean> parameter = new HashMap<String, CriteriaNameBean>();
		cmsCaseService.queryBeanList(
							"select count(*) from t_case where 1 = 1 ", 
							"select t.case_id,t.case_name,t.employ,c.category_name,i.category_name as industry_name, t.is_show, t.update_date "
							+ "from t_case t "
							+ "left join t_case_category c on t.case_category_id = c.case_category_id "
							+ "left join t_industry_category i on t.industry_category_id = i.industry_category_id "
							+ "where 1 = 1 ", 
							parameter, page, CaseModel.class, "order by t.sort_no");
		return page;
	}
	
	@RequestMapping("/edit")
	public ModelAndView caseEditorView(Model model, HttpServletRequest req){
		ModelAndView view = new ModelAndView();
		
		editPreOption(model, req, Case.class);
		List<CaseCategory> categories = cmsCaseService.queryByMappingProperty(null, CaseCategory.class);
		List<IndustryCategory> industries = cmsCaseService.queryByMappingProperty(null, IndustryCategory.class);
		
		model.addAttribute("industries", industries);
		model.addAttribute("categories", categories);
		view.setViewName("case-edit");
		return view;
	}
	
	@RequestMapping("/edited")
	public ResultObject caseEdit(@RequestBody Case cases, HttpServletRequest req){
		ResultObject result = new ResultObject();
		
		cmsCaseService.addOrUpdateCase(cases);
		
		return result;
	}
	
	@RequestMapping("/delete")
	public ResultObject categoryDel(Model model, HttpServletRequest req){
		return delPreOption(req, Case.class);
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
