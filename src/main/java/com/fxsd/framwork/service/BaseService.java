package com.fxsd.framwork.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;	

/**
 * Service 通用基类
 * @author ChenJianhui
 * @param <T>
 */
@NoRepositoryBean
public interface BaseService<T, ID extends Serializable> {
	
	/**
	 * 根据删除一个实体
	 * @param id
	 */
	public void delete(ID id);
	
	/**
	 * 根据实体对象删除一个实体
	 * @param entity
	 */
	public void delete(T entity) ;
	
	/**
	 * 根据实体集合批量删除实体
	 * @param entities
	 */
	public void delete(Iterable<? extends T> entities);
	
	/**
	 * 根据实体集合批量删除
	 * @param entities
	 */
	public void deleteInBatch(Iterable<T> entities);
	
	/**
	 * 删除所有的实体
	 */
	public void deleteAll();
	
	/**
	 * 根据ID查询一个实体(有延迟加载)
	 * @param id
	 * @return
	 */
	public T getOne(ID id);
	
	/**
	 * 根据ID查询一个实体
	 * @param id
	 * @return
	 */
	public T findOne(ID id);
	
	/**
	 * 根据ID判断一个实体是否存在
	 * @param id
	 * @return
	 */
	public boolean exists(ID id);
	
	/**
	 * 查询所有的实体
	 * @return
	 */
	public List<T> findAll() ;
	
	/**
	 * 根据ID集合查询实体集合
	 * @param ids
	 * @return
	 */
	public List<T> findAll(Iterable<ID> ids);
	
	/**
	 * 排序查询所有实体
	 * @param sort
	 * @return
	 */
	public List<T> findAll(Sort sort) ;
	
	/**
	 * 分页查询所有的实体
	 * @param pageable
	 * @return
	 */
	public Page<T> findAll(Pageable pageable);
	
	/**
	 * 按自定义条件查询一个实体
	 * @param spec
	 * @return
	 */
	public T findOne(Specification<T> spec);

	/**
	 * 按自定义条件查询所有实体
	 * @param spec
	 * @return
	 */
	public List<T> findAll(Specification<T> spec) ;
	
	/**
	 * 按自定义条件分页查询所有实体
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<T> findAll(Specification<T> spec, Pageable pageable) ;
	
	/**
	 * 根据自定义条件排序查询实体
	 * @param spec
	 * @param sort
	 * @return
	 */
	public List<T> findAll(Specification<T> spec, Sort sort);
	
	/**
	 * 统计所有实体的数量
	 * @return
	 */
	public long count() ;
	
	/**
	 * 按条件统计实体的数量
	 * @param spec
	 * @return
	 */
	public long count(Specification<T> spec) ;
	
	/**
	 * 保存后更新一个实体
	 * @param entity
	 * @return
	 */
	public <S extends T> S save(S entity) ;
	
	/**
	 * 批量保存更新实体
	 * @param entities
	 * @return
	 */
	public <S extends T> List<S> save(Iterable<S> entities);

	/**
	 * 获取Service对象的实体类
	 * @return
	 */
	Class<T> getEntityClazz();


}
