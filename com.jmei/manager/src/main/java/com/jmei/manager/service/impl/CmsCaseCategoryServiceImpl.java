package com.jmei.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jmei.manager.service.ICmsCaseCategoryService;
import com.jmei.models.entity.CaseCategory;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsCaseCategoryService")
public class CmsCaseCategoryServiceImpl extends CommonServiceImpl implements ICommonService, ICmsCaseCategoryService{
	@Override
	public void addOrUpdateCaseCategory(CaseCategory caseCategory) {
		Date curDate = new Date(System.currentTimeMillis());
		caseCategory.setUpdateDate(curDate);
		
		if(StringUtils.hasText(caseCategory.getCaseCategoryId())){
			update(caseCategory);
		} else {
			caseCategory.setCreateDate(curDate);
			add(caseCategory);
		}
	}
}
