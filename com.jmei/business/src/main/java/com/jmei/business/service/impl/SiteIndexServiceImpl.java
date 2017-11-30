package com.jmei.business.service.impl;

import org.springframework.stereotype.Service;

import com.jmei.business.service.ISiteIndexService;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("siteIndexService")
public class SiteIndexServiceImpl extends CommonServiceImpl implements ICommonService, ISiteIndexService{

}
