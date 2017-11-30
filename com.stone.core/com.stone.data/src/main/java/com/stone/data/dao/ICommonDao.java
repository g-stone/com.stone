/**
 * ICommonDao.java
 * @author lixinpeng
 * @DATE: 2017年10月9日 @TIME: 上午10:16:03
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.data.dao;

/**
 * 功能说明：数据库操作DAO层基础API接口，所有基于数据库操作的DAO层接口类都继承该接口
 *
 * @author lixinpeng
 * @DATE: 2017年10月9日 @TIME: 上午10:16:03
 */
public interface ICommonDao extends IHibernateDao, IJdbcDao{
}
