package com.jmei.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jmei.manager.service.ICmsCaseIndustryService;
import com.jmei.models.entity.IndustryCategory;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsCaseIndustryService")
public class CmsCaseIndustryServiceImpl extends CommonServiceImpl implements ICommonService, ICmsCaseIndustryService{
	@Override
	public void addOrUpdateIndustryCategory(IndustryCategory industryCategory) {
		Date curDate = new Date(System.currentTimeMillis());
		industryCategory.setUpdateDate(curDate);
		
		if(StringUtils.hasText(industryCategory.getIndustryCategoryId())){
			update(industryCategory);
		} else {
			industryCategory.setCreateDate(curDate);
			add(industryCategory);
		}
	}
}
