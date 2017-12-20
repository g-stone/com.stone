package com.jmei.manager.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jmei.manager.service.ICmsCaseCategoryService;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsCaseCategoryService")
public class CmsCaseCategoryServiceImpl extends CommonServiceImpl implements ICommonService, ICmsCaseCategoryService{
	private ICmsCaseCategoryService cmsCaseCategoryService;

	public ICmsCaseCategoryService getCmsCaseCategoryService() {
		return cmsCaseCategoryService;
	}
	@Resource(name = "cmsCaseCategoryService")
	public void setCmsCaseCategoryService(ICmsCaseCategoryService cmsCaseCategoryService) {
		this.cmsCaseCategoryService = cmsCaseCategoryService;
	}
}
