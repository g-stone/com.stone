/**
 * JdbcDao.java
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:29:07
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.data.dao;

import java.util.List;

/**
 * 功能说明：数据库JDBC操作DAO层API接口
 *
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:29:07
 */
public interface IJdbcDao {
	/**
	 * 功能说明：对象批量保存操作
	 * IJdbcDao.saveBatch();
	 * @author: lixinpeng
	 * @DATE: 2017年10月23日  @TIME: 上午8:45:48
	 * @param list
	 */
	<T> void saveBatch(List<T> list);
	
	/**
	 * 功能说明：对象批量更新操作
	 * IJdbcDao.updateBatch();
	 * @author: lixinpeng
	 * @DATE: 2017年10月23日  @TIME: 上午8:46:11
	 * @param list
	 */
	<T> void updateBatch(List<T> list);
	
	/**
	 * 功能说明：对象批量保存或更新操作
	 * IJdbcDao.saveOrUpdateBatch();
	 * @author: lixinpeng
	 * @DATE: 2017年10月23日  @TIME: 上午8:46:36
	 * @param list
	 */
	<T> void saveOrUpdateBatch(List<T> list);
	
	/**
	 * 功能说明：查询给定的对象是否存在
	 * IJdbcDao.queryIsExist();
	 * @author: lixinpeng
	 * @DATE: 2017年10月23日  @TIME: 上午8:47:00
	 * @param t
	 * @return
	 */
	<T> boolean queryIsExist(T t);
}
