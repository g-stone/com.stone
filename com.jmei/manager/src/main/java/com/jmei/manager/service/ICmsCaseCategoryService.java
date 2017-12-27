package com.jmei.manager.service;

import com.jmei.models.entity.CaseCategory;
import com.stone.data.service.ICommonService;

public interface ICmsCaseCategoryService extends ICommonService{
	
	void addOrUpdateCaseCategory(CaseCategory caseCategory);

}
