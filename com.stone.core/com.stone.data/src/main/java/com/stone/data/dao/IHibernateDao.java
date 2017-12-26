/**
 * HibernateDao.java
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:28:46
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.data.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageSupport;

/**
 * 功能说明：数据库BIBERNATE操作DAO层API
 *
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:28:46
 */
public interface IHibernateDao {
	/**
	 * 功能说明：保存实体对象
	 * IHibernateDao.save();
	 * @author: lixinpeng
	 * @DATE: 2017年9月23日  @TIME: 下午5:58:34
	 * @param entity
	 * @return
	 */
	<T> Serializable save(T entity);
	
	/**
	 * 功能说明：更新实体对象
	 * IHibernateDao.update();
	 * @author: lixinpeng
	 * @DATE: 2017年9月23日  @TIME: 下午5:58:56
	 * @param entity
	 */
	<T> void update(T entity);
	
	/**
	 * 功能说明：保存或更新实体对象
	 * IHibernateDao.saveOrUpdate();
	 * @author: lixinpeng
	 * @DATE: 2017年9月23日  @TIME: 下午5:59:15
	 * @param entity
	 */
	<T> void saveOrUpdate(T entity);
	
	/**
	 * 功能说明：依据主键获取对应的实体对象
	 * IHibernateDao.get();
	 * @author: lixinpeng
	 * @DATE: 2017年9月23日  @TIME: 下午5:59:40
	 * @param clz
	 * @param key
	 * @return
	 */
	<T> T get(Class<T> clz, Serializable key);
	
	/**
	 * 功能说明：删除对象
	 * IHibernateDao.delete();
	 * @author: lixinpeng
	 * @DATE: 2017年10月10日  @TIME: 上午8:58:08
	 * @param entity
	 * @return
	 */
	<T> void delete(T entity);
	
	/**
	 * 功能说明：删除对象
	 * IHibernateDao.delete();
	 * @author: lixinpeng
	 * @DATE: 2017年10月10日  @TIME: 上午8:58:08
	 * @param clz
	 * @param key
	 * @return
	 */
	<T> void delete(Class<T> clz, Serializable key);
	
	/**
	 * 功能说明：通过属性映射获取对象
	 * IHibernateDao.getByMappingProperty();
	 * @author: lixinpeng
	 * @DATE: 2017年10月10日  @TIME: 上午8:58:08
	 * @param parameter
	 * @param c
	 * @return
	 */
	<T> T getByMappingProperty(Map<String, Object> parameter, Class<T> c);
	
	/**
	 * 功能说明：通过属性映射获取符合条件的对象列表
	 * IHibernateDao.queryByMappingProperty();
	 * @author: lixinpeng
	 * @DATE: 2017年10月10日  @TIME: 上午8:58:57
	 * @param parameter
	 * @param c
	 * @return
	 */
	<T> List<T> queryByMappingProperty(Map<String, Object> parameter, Class<T> c);
	
	/**
	 * 功能说明：查询给定的条件记录是否存在
	 * IHibernateDao.queryCount();
	 * @author: lixinpeng
	 * @DATE: 2017年10月23日  @TIME: 上午9:56:40
	 * @param countSql
	 * @param parameter
	 * @return
	 */
	Integer queryCount(String countSql, Map<String, CriteriaNameBean> parameter);
	
	/**
	 * 功能说明：分页查询
	 * IHibernateDao.queryBeanList();
	 * @author: lixinpeng
	 * @DATE: 2017年10月23日  @TIME: 上午10:12:53
	 * @param countSql
	 * @param dataSql
	 * @param parameter
	 * @param page
	 * @param _class
	 * @param orderSort
	 * @return
	 */
	<T> PageSupport<T> queryBeanList(String countSql, String dataSql, Map<String, CriteriaNameBean> parameter, PageSupport<T> page, Class<T> _class, String orderSort);
}
