package com.jmei.manager.service.impl;

import org.springframework.stereotype.Service;

import com.jmei.manager.service.ICmsCaseService;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsCaseService")
public class CmsCaseServiceImpl extends CommonServiceImpl implements ICommonService, ICmsCaseService{

}
