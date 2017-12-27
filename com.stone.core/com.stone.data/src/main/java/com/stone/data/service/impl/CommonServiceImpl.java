package com.stone.data.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.stone.data.dao.ICommonDao;
import com.stone.data.service.ICommonService;
import com.stone.tools.jdbc.CriteriaNameBean;
import com.stone.tools.jdbc.PageSupport;

@Service("commonService")
public class CommonServiceImpl implements ICommonService{
	protected ICommonDao commonDao;
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	@Resource(name = "commonDao")
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}
	@Override
	public <T> void addBatch(List<T> list) {
		commonDao.saveBatch(list);
	}
	@Override
	public <T> void updateBatch(List<T> list) {
		commonDao.updateBatch(list);
	}
	@Override
	public <T> void addOrUpdateBatch(List<T> list) {
		commonDao.saveOrUpdateBatch(list);
	}
	@Override
	public <T> boolean queryIsExist(T t) {
		return commonDao.queryIsExist(t);
	}
	@Override
	public <T> Serializable add(T entity) {
		return commonDao.save(entity);
	}
	@Override
	public <T> void update(T entity) {
		commonDao.update(entity);
	}
	@Override
	public <T> void addOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);
	}
	@Override
	public <T> T get(Class<T> clz, Serializable key) {
		return commonDao.get(clz, key);
	}
	@Override
	public <T> T getByMappingProperty(Map<String, Object> parameter, Class<T> c) {
		return commonDao.getByMappingProperty(parameter, c);
	}
	@Override
	public <T> List<T> queryByMappingProperty(Map<String, Object> parameter, Class<T> c) {
		return commonDao.queryByMappingProperty(parameter, c);
	}
	@Override
	public Integer queryCount(String countSql, Map<String, CriteriaNameBean> parameter) {
		return commonDao.queryCount(countSql, parameter);
	}
	@Override
	public <T> PageSupport<T> queryBeanList(String countSql, String dataSql, Map<String, CriteriaNameBean> parameter,
			PageSupport<T> page, Class<T> _class, String orderSort) {
		return commonDao.queryBeanList(countSql, dataSql, parameter, page, _class, orderSort);
	}
	@Override
	public <T> void delete(T entity) {
		commonDao.delete(entity);
	}
	@Override
	public <T> void delete(Class<T> clz, Serializable key) {
		commonDao.delete(clz, key);
	}
}
