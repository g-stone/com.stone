package com.jmei.manager.service;

import com.jmei.models.entity.Case;
import com.stone.data.service.ICommonService;

public interface ICmsCaseService extends ICommonService{
	void addOrUpdateCase(Case cases);
}
