/**
 * CommonDao.java
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:46:06
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.data.dao.imp;

import org.springframework.stereotype.Repository;

import com.stone.data.dao.ICommonDao;
import com.stone.data.dao.IHibernateDao;
import com.stone.data.dao.IJdbcDao;

/**
 * 功能说明：数据库操作DAO层基础实现类，所有数据库操作实现类都继承该类
 *
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:46:06
 */
@Repository("commonDao")
public class CommonDao extends WrapBridgeDao implements ICommonDao, IHibernateDao, IJdbcDao{
	
}
