package com.jmei.manager.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmei.manager.service.ICmsCaseIndustryService;

/**
 * 案例行业分类控制器
 * @author dba
 *
 */
@RequestMapping("/spring/cms")
@RestController("cmsCaseIndustryController")
public class CmsCaseIndustryController {
	private ICmsCaseIndustryService cmsCaseIndustryService;

	public ICmsCaseIndustryService getCmsCaseIndustryService() {
		return cmsCaseIndustryService;
	}
	@Resource(name = "cmsCaseIndustryService")
	public void setCmsCaseIndustryService(ICmsCaseIndustryService cmsCaseIndustryService) {
		this.cmsCaseIndustryService = cmsCaseIndustryService;
	}
}
