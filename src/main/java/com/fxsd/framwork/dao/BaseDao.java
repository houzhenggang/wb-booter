package com.fxsd.framwork.dao;

import com.fxsd.framwork.jdbc.SprocParamter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * DAO基类
 * @author ChenJianhui
 * @param <T>
 */
@NoRepositoryBean
 public interface BaseDao<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>{
	
	
	/**
	 * 按照JPQL语句进行批量更新
	 * @param jpql
	 * @param params
	 */
	 int executeByJPQL(String jpql, Object... params);
	
	/**
	 * 执行原生的sql语句
	 * @param sql
	 * @param params
	 */
	 int executeBySQL(String sql,Object...params);

	/**
	 * 根据JPQL语句查询
	 * @param jpql
	 * @param params
	 * @return
	 */
	 List<T> queryByJPQL(String jpql, Object... params);
	
	/**
	 * 根据JPQL语句分页查询
	 * @param jpql
	 * @param page
	 * @param params
	 * @return
	 */
	 Page<T> queryByJPQL(String jpql, Pageable page, Object... params);
	
	
	/**
	 * 单值检索,确保查询结果有且只有一条记录
	 * @param jpql
	 * @param params
	 * @return
	 */
	 Object uniqueResultByJPQL(String jpql, Object...params);
	
	
	/**
	 * 单值检索,确保查询结果有且只有一条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	 Object uniqueResultBySQL(String sql, Object...params);
	
	/**
	 * 执行原生的SQL查询，自定义映射实体
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	 <E> List<E> queryBySQL(Class<E> clazz, String sql, Object...params);
	
	/**
	 * 自定义分页查询
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	 <E> Page<E> queryBySQL(Class<E> clazz, String sql, Pageable page, Object...params);
	
	/**
	 * 执行原生的JPQL查询，自定义映射实体
	 * @param clazz 自定义映射实体类
	 * @param jpql
	 * @param params
	 * @return
	 */
	 <E> List<E> queryByJPQL(Class<E> clazz,String jpql, Object...params);
	
	/**
	 * 自定义JPQL new map(c1, c2, c3,......)方式查询映射到POJO对象
	 * @param clazz
	 * @param jpql
	 * @param params
	 * @return
	 */
	<E> List<E> queryByMapJPQL(Class<E> clazz, String jpql, Object...params);
	

	/**
	 * 自定义JPQL语法 new map(c1, c2, c3,......)方式分页查询映射到POJO对象
	 * @param clazz
	 * @param jpql
	 * @param page
	 * @param params
	 * @return
	 */
	<E> Page<E> queryPageByMapJPQL(Class<E> clazz, String jpql, Pageable page, Object... params);
	
	/**
	 * 自定义映射POJO分页查询
	 * 在自定义的DTO中加入注解 @NativeQueryResultEntity， @NativeQueryResultColumn
	 * @param clazz
	 * @param sql
	 * @param page
	 * @param params
	 * @return
	 */
	<E> Page<E> queryPOJOBySQL(Class<E> clazz, String sql,Pageable page, Object...params);
	
	/**
	 * 自定义映射POJO查询
	 * 在自定义的DTO中加入注解 @NativeQueryResultEntity， @NativeQueryResultColumn
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	<E> List<E> queryPOJOBySQL(Class<E> clazz, String sql, Object... params);

	/**
	 * 自定义查询， 返回List<Object[]>
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Object[]> queryArrBySQL(String sql, Object...params);

	/**
	 * 调用返回单个结果集的存储过程
	 * @param clazz
	 * @param procName
	 * @param params
	 * @return
	 */
	<E> List<E> queryPOJOByProc(Class<E> clazz, String procName, List<SprocParamter> params);

	/**
	 * 调用返回多个结果集的存储过程
	 * @param clazzs
	 * @param procName
	 * @param paramList
	 * @return
	 */
	List<List<?>> queryMultiResultSetByProc(List<Class<?>> clazzs, String procName, List<SprocParamter> paramList);

	/**
	 * 调用存储过程查询
	 * @param clazz
	 * @param procName
	 * @param paramList
	 * @return
	 */
	<E> List<E> queryEntityByProc(Class<E> clazz, String procName, List<SprocParamter> paramList);

	/**
	 * 传入参数集合进行查询
	 * @param clazz
	 * @param sql
	 * @param page
	 * @param params
	 * @return
	 */
	<E> Page<E> queryPOJOBySQLWithListParam(Class<E> clazz, String sql, Pageable page, List<Object> params);

	/**
	 * 传入参数集合进行查询
	 * @param sql
	 * @param params
	 * @return
	 */
	Object uniqueResultBySQLWithListParams(String sql, List<Object> params);

	/**
	 * 传入参数集合进行查询
	 * @param jpql
	 * @param page
	 * @param params
	 * @return
	 */
	Page<T> queryByJPQLWithListParam(String jpql, Pageable page, List<Object> params);
	
	

}
