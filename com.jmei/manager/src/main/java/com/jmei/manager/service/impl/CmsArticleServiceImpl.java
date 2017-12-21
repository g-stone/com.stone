package com.jmei.manager.service.impl;

import org.springframework.stereotype.Service;

import com.jmei.manager.service.ICmsArticleService;
import com.stone.data.service.ICommonService;
import com.stone.data.service.impl.CommonServiceImpl;

@Service("cmsArticleService")
public class CmsArticleServiceImpl extends CommonServiceImpl implements ICommonService, ICmsArticleService{

}
