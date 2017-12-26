/**
 * WrapBridgeDao.java
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:33:51
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.data.dao.imp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stone.data.dao.IHibernateDao;
import com.stone.data.dao.IJdbcDao;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageSupport;
import com.stone.tools.jdbc.ResultBeanTransformer;
import com.stone.tools.jdbc.tools.ReflectUtil;
/**
 * 功能说明：数据库DAO层操作API实现类
 *
 * @author lixinpeng
 * @DATE: 2017年9月23日 @TIME: 下午5:33:51
 */
@Repository("wrapBridgeDao")
public abstract class WrapBridgeDao implements IHibernateDao, IJdbcDao{
	private static Logger logger = Logger.getLogger(WrapBridgeDao.class);
	
	protected JdbcTemplate jdbcTemplate;
	protected SessionFactory sessionFactory;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Resource(name = "jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public <T> Serializable save(T entity) {
		Serializable identify = null;
		try {
			identify = sessionFactory.getCurrentSession().save(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("保存实体成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("保存实体异常", e);
			throw e;
		}
		return identify;
	}
	
	@Override
	public <T> void update(T entity) {
		try {
			sessionFactory.getCurrentSession().update(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("更新实体成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("更新实体异常", e);
			throw e;
		}
	}
	
	@Override
	public <T> void saveOrUpdate(T entity) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("更新或保存实体成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("更新或保存实体异常", e);
			throw e;
		}
	}
	
	@Override
	public <T> T get(Class<T> clz, Serializable key) {
		return sessionFactory.getCurrentSession().get(clz, key);
	}
	
	@Override
	public <T> void delete(T entity){
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	@Override
	public <T> void delete(Class<T> clz, Serializable key){
		T obj = get(clz, key);
		
		if(obj != null){
			delete(obj);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getByMappingProperty(Map<String, Object> parameter, Class<T> c){
		T result = null;
		String hql = "from " + c.getSimpleName() + " where 1 = 1 ";
		
		if(parameter != null && !parameter.isEmpty()){
			for(String name:parameter.keySet()){
				hql += "and " + (name.contains("_") ? name.substring(0, name.indexOf("_")) : name) + " = :" + name.toLowerCase() + " ";
			}
		}
		try {
			Query<T> query = sessionFactory.getCurrentSession().createQuery(hql);
			if(parameter != null && !parameter.isEmpty()){
				for(Map.Entry<String, Object> entry:parameter.entrySet()){
					query.setParameter(entry.getKey().toLowerCase(), entry.getValue());
				}
			}
			result = query.uniqueResult();
			if (logger.isDebugEnabled()) {
				logger.debug("查询异常:" + c.getName() + ",参数:" + parameter);
			}
		} catch (RuntimeException e) {
			logger.error("查询异常", e);
			throw e;
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> queryByMappingProperty(Map<String, Object> parameter, Class<T> c){
		List<T> results = null;
		String hql = "from " + c.getSimpleName() + " where 1 = 1 ";
		
		if(parameter != null && !parameter.isEmpty()){
			for(String name:parameter.keySet()){
				hql += "and " + (name.contains("_") ? name.substring(0, name.indexOf("_")) : name) + " = :" + name.toLowerCase() + " ";
			}
		}
		try {
			Query<T> query = sessionFactory.getCurrentSession().createQuery(hql);
			if(parameter != null && !parameter.isEmpty()){
				for(Map.Entry<String, Object> entry:parameter.entrySet()){
					query.setParameter(entry.getKey().toLowerCase(), entry.getValue());
				}
			}
			results = query.list();
			if (logger.isDebugEnabled()) {
				logger.debug("查询异常:" + c.getName() + ",参数:" + parameter);
			}
		} catch (RuntimeException e) {
			logger.error("查询异常", e);
			throw e;
		}
		
		return results;
	}
	
	@Override
	public <T> void saveBatch(List<T> list){
		if(list == null || list.isEmpty()){
			return;
		}
		
		String sql = ReflectUtil.parseInsertSql(list.get(0));
		int[] metaTypes = null;
		List<Object[]> batchData = new ArrayList<Object[]>();
		for(T entity:list){
			ReflectUtil.DataMeta meta = ReflectUtil.parseDataMeta(entity);
			batchData.add(meta.getParameter());
			if(metaTypes == null){
				metaTypes = meta.getMetaType();
			}
		}
		
		int[] effect = jdbcTemplate.batchUpdate(sql, batchData, metaTypes);
		
		logger.info("\n****************************************************"
				  + "\n*****批量保存SQL:" + sql 
				  + "\n*****预期条数：" + batchData.size() 
				  + "\n*****执行结果：" + Arrays.toString(effect));
	}
	
	@Override
	public <T> void updateBatch(List<T> list){
		if(list == null || list.isEmpty()){
			return;
		}
		
		String sql = ReflectUtil.parseUpdateSql(list.get(0));
		int[] metaTypes = null;
		List<Object[]> batchData = new ArrayList<Object[]>();
		for(T entity:list){
			ReflectUtil.DataMeta meta = ReflectUtil.parseUpdateMeta(entity);
			batchData.add(meta.getParameter());
			if(metaTypes == null){
				metaTypes = meta.getMetaType();
			}
		}
		
		int[] effect = jdbcTemplate.batchUpdate(sql, batchData, metaTypes);
		
		logger.info("\n****************************************************"
				  + "\n*****批量更新SQL:" + sql 
				  + "\n*****预期条数：" + batchData.size() 
				  + "\n*****执行结果：" + Arrays.toString(effect));
	}
	
	@Override
	public <T> void saveOrUpdateBatch(List<T> list){
		List<T> insertList = new ArrayList<T>();
		List<T> updateList = new ArrayList<T>();
		for(T t:list){
			if(queryIsExist(t)){
				updateList.add(t);
			}else{
				insertList.add(t);
			}
		}
		
		if(!insertList.isEmpty()){
			saveBatch(insertList);
		}
		if(!updateList.isEmpty()){
			updateBatch(updateList);
		}
	}
	
	@Override
	public <T> boolean queryIsExist(T t){
		String counter = ReflectUtil.parseExistSql(t);
		ReflectUtil.DataMeta meta = ReflectUtil.parseExistMeta(t);
		
		BigDecimal count = jdbcTemplate.queryForObject(counter, 
								meta.getParameter(), 
								meta.getMetaType(), 
								BigDecimal.class);
		
		return (count.compareTo(BigDecimal.ZERO) == 0 ? false : true);
	}
	
	@Override
	public Integer queryCount(String countSql, Map<String, CriteriaNameBean> parameter){
		Session tmpSession = sessionFactory.getCurrentSession();
		
		String realCountSql = countSql;
		
		if (parameter != null && !parameter.isEmpty()) {
			for (CriteriaNameBean criteria: parameter.values()) {
				realCountSql += criteria.criteriaString();
			}
		}
		
		NativeQuery<?> tmpSQLQuery = tmpSession.createNativeQuery(realCountSql);
		if (parameter != null && !parameter.isEmpty()) {
			for (CriteriaNameBean criteria: parameter.values()) {
				if(criteria.isApplyCriteria()){
					tmpSQLQuery.setParameter(criteria.getPlaceholderName(), criteria.getCriteriaValue());
				}
			}
		}
		
		return ((BigInteger) tmpSQLQuery.uniqueResult()).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public <T> PageSupport<T> queryBeanList(String countSql, String dataSql, Map<String, CriteriaNameBean> parameter, PageSupport<T> page, Class<T> _class, String orderSort){
		Session tmpSession = sessionFactory.getCurrentSession();
		
		Map<String, CriteriaNameBean> realCriteriaInfo = new HashMap<String, CriteriaNameBean>();
		String realCounter = countSql;
		String realDataSql = dataSql;
		if (parameter != null && !parameter.isEmpty()) {
			for (CriteriaNameBean criteria: parameter.values()) {
				realDataSql += criteria.criteriaString();
				if(criteria.isApplyCriteria()){
					realCriteriaInfo.put(criteria.getPlaceholderName(), criteria);
				}
			}
		}
		
		realDataSql += " " + orderSort;
		
		Integer totalCount = queryCount(realCounter, realCriteriaInfo);
		
		page.setRows(totalCount);
		
		logger.info(realDataSql);
		logger.info(realCriteriaInfo);
		
		NativeQuery<T> tmpSQLQuery = tmpSession.createNativeQuery(realDataSql);
		if (!realCriteriaInfo.isEmpty()) {
			for (CriteriaNameBean criteria: realCriteriaInfo.values()) {
				tmpSQLQuery.setParameter(criteria.getPlaceholderName(), criteria.getCriteriaValue());
			}
		}
		
		if(!page.isAll()){
			if (page.getSize() != -1) {
				tmpSQLQuery.setFirstResult(page.getFirstFetchIndex());
				tmpSQLQuery.setFetchSize(page.getSize());
				tmpSQLQuery.setMaxResults(page.getSize());
			}
		}
		tmpSQLQuery.setResultTransformer(new ResultBeanTransformer<T>(_class));
		List<T> beanInfos = tmpSQLQuery.list();
		page.setBeanList(beanInfos);
		if (page.getSize() == -1 && beanInfos != null) {
			page.setSize(beanInfos.size());
		}
		
		return page;
	}
}
