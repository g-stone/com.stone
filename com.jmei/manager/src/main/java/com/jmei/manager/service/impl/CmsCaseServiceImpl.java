package com.jmei.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jmei.manager.service.ICmsCaseService;
import com.jmei.models.entity.Case;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsCaseService")
public class CmsCaseServiceImpl extends CommonServiceImpl implements ICommonService, ICmsCaseService{
	@Override
	public void addOrUpdateCase(Case cases) {
		Date curDate = new Date(System.currentTimeMillis());
		cases.setUpdateDate(curDate);
		
		if(StringUtils.hasText(cases.getCaseId())){
			update(cases);
		} else {
			cases.setCreateDate(curDate);
			add(cases);
		}
	}
}
