package com.yaoa.boot.app.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.yaoa.boot.app.dao.BaseDao;
import com.yaoa.boot.app.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;


/**
 * BaseService 抽象实现类
 * @author ChenJianhui
 * @param <T>
 */
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {
	
	@Autowired
	protected BaseDao<T, ID> dao;
	
	private Class<T> clazz;
	
	@Override
	public Class<T> getEntityClazz() {
		return clazz;
	}

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	@Transactional
	public void delete(ID id) {
		dao.delete(id);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		dao.delete(entity);
	}

	@Override
	@Transactional
	public void delete(Iterable<? extends T> entities) {
		dao.delete(entities);
	}

	@Override
	@Transactional
	public void deleteInBatch(Iterable<T> entities) {
		dao.deleteInBatch(entities);
	}

	@Override
	@Transactional
	public void deleteAll() {
		dao.deleteAll();
	}

	@Override
	public T getOne(ID id) {
		return dao.getOne(id);
	}
	
	@Override
	public T findOne(ID id){
		return dao.findOne(id);
	}

	@Override
	public boolean exists(ID id) {
		return dao.exists(id);
	}

	@Override
	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	public List<T> findAll(Iterable<ID> ids) {
		return dao.findAll(ids);
	}

	@Override
	public List<T> findAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public T findOne(Specification<T> spec) {
		return dao.findOne(spec);
	}

	@Override
	public List<T> findAll(Specification<T> spec) {
		return dao.findAll(spec);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return dao.findAll(spec, pageable);
	}

	@Override
	public List<T> findAll(Specification<T> spec, Sort sort) {
		return dao.findAll(spec, sort);
	}

	@Override
	public long count() {
		return dao.count();
	}

	@Override
	public long count(Specification<T> spec) {
		return dao.count(spec);
	}

	@Override
	@Transactional
	public <S extends T> S save(S entity) {
		return dao.save(entity);
	}

	@Override
	@Transactional
	public <S extends T> List<S> save(Iterable<S> entities) {
		return dao.save(entities);
	}

}
