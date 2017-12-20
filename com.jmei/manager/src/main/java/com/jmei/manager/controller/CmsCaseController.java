package com.jmei.manager.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmei.manager.service.ICmsCaseService;

/**
 * 案例控制器
 * @author dba
 */
@RequestMapping("/spring/cms")
@RestController("cmsCaseController")
public class CmsCaseController {
	private ICmsCaseService cmsCaseService;
	
	public ICmsCaseService getCmsCaseService() {
		return cmsCaseService;
	}
	@Resource(name = "cmsCaseService")
	public void setCmsCaseService(ICmsCaseService cmsCaseService) {
		this.cmsCaseService = cmsCaseService;
	}
}
