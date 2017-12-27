package com.jmei.manager.service;

import com.jmei.models.entity.IndustryCategory;
import com.stone.data.service.ICommonService;

public interface ICmsCaseIndustryService extends ICommonService{
	void addOrUpdateIndustryCategory(IndustryCategory industryCategory);
}
