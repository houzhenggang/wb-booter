package com.yaoa.boot.app.dao.impl;

import com.yaoa.boot.app.annotation.NativeQueryResultColumn;
import com.yaoa.boot.app.dao.BaseDao;
import com.yaoa.boot.app.entity.BaseEntity;
import com.yaoa.boot.app.jdbc.SprocParamter;
import com.yaoa.boot.app.util.ValidUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * BaseDao基于Hibernate的实现类
 * @author ChenJianhui
 * @param <T, ID>
 */
@NoRepositoryBean
public class BaseDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {
	
	private EntityManager em;
	
	public static final String COUNT_QUERY_STRING = "select count(%s) from %s x where x.deleted = false";
	
	public static final String EXISTS_QUERY_STRING = "select count(%s) from %s x where x.%s = :id and x.deleted = false";
	
	public BaseDaoImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.em = em;
	}

	@Override
	public int executeByJPQL(String jpql, Object... params) {
		Query q = em.createQuery(jpql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return q.executeUpdate();
	}

	@Override
	public int executeBySQL(String sql, Object... params) {
		Query q = em.createNativeQuery(sql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByJPQL(String jpql, Object... params) {
		Query q = em.createQuery(jpql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return q.getResultList();
	}

	@Override
	public Object uniqueResultByJPQL(String jpql, Object... params) {
		Query q = em.createQuery(jpql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return q.getSingleResult();
	}
	
	@Override
	public Object uniqueResultBySQL(String sql, Object... params) {
		Query q = em.createNativeQuery(sql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return q.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> queryBySQL(Class<E> clazz, String sql, Object... params) {
		Query q = em.createNativeQuery(sql,clazz);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> queryByJPQL(Class<E> clazz, String jpql, Object... params) {
		Query q = em.createQuery(jpql, clazz);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return q.getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Page<T> queryByJPQLWithListParam(String jpql, Pageable page, List<Object> params) {
		Query q = em.createQuery(jpql);
		for(int i = 0 ; i < params.size() ; i ++){
			q.setParameter(i+1, params.get(i));
		}
		q.setFirstResult(page.getOffset());
		q.setMaxResults(page.getPageSize());
		String jpqlTmp = jpql.toLowerCase();
		String countSql = "select count(*) " + jpql.substring(jpqlTmp.indexOf("from"));
		BigInteger count = (BigInteger) this.uniqueResultBySQLWithListParams(countSql, params);
		return new PageImpl<T>(q.getResultList(), page, count.longValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<T> queryByJPQL(String jpql, Pageable page, Object... params) {
		Query q = em.createQuery(jpql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		q.setFirstResult(page.getOffset());
		q.setMaxResults(page.getPageSize());
		String jpqlTmp = jpql.toLowerCase();
		String countSql = "select count(*) " + jpql.substring(jpqlTmp.indexOf("from"));
		BigInteger count = (BigInteger) this.uniqueResultBySQL(countSql, params);
		return new PageImpl<T>(q.getResultList(), page, count.longValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> Page<E> queryBySQL(Class<E> clazz, String sql, Pageable page, Object... params) {
		Query q = em.createNativeQuery(sql, clazz);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		q.setFirstResult(page.getOffset());
		q.setMaxResults(page.getPageSize());
		String sqlTmp = sql.toLowerCase(); 
		String countSql = "select count(*) " + sql.substring(sqlTmp.indexOf("from"));
		BigInteger count = (BigInteger) this.uniqueResultBySQL(countSql, params);
		return new PageImpl<E>(q.getResultList(), page, count.longValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> queryByMapJPQL(Class<E> clazz, String jpql, Object... params) {
		Query q = em.createQuery(jpql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		List<Map<String, Object>> resultMaps = q.getResultList();
		List<E> resultList = null;
		if(ValidUtils.isValid(resultMaps)){
			resultList = new ArrayList<E>(resultMaps.size());
			for (Map<String,Object> map : resultMaps) {
				try {
					resultList.add(assemble(map, clazz));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> Page<E> queryPageByMapJPQL(Class<E> clazz, String jpql, Pageable page, Object... params) {
		Query q = em.createQuery(jpql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		q.setFirstResult(page.getOffset());
		q.setMaxResults(page.getPageSize());
		List<Map<String, Object>> resultMaps = q.getResultList();
		List<E> resultList = null;
		if(ValidUtils.isValid(resultMaps)){
			resultList = new ArrayList<E>(resultMaps.size());
			for (Map<String,Object> map : resultMaps) {
				try {
					resultList.add(assemble(map, clazz));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String sqlTmp = jpql.toLowerCase(); 
			String countSql = "select count(*) " + jpql.substring(sqlTmp.indexOf("from"));
			BigInteger count = (BigInteger) this.uniqueResultBySQL(countSql, params);
			return new PageImpl<E>(resultList, page, count.longValue());
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E> Page<E> queryPOJOBySQL(Class<E> clazz, String sql,Pageable page, Object...params){
		Query q = em.createNativeQuery(sql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		q.setFirstResult(page.getOffset());
		q.setMaxResults(page.getPageSize());
		List<Object[]> results = q.getResultList();
		List<E> list = this.map(results, clazz);
		String countSql = "select count(1) from (" + sql + ") tableTemp";
		BigInteger count = (BigInteger) this.uniqueResultBySQL(countSql, params);
		return new PageImpl<E>(list, page, count.longValue());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E> Page<E> queryPOJOBySQLWithListParam(Class<E> clazz, String sql,Pageable page, List<Object> params){
		Query q = em.createNativeQuery(sql);
		for(int i = 0 ; i < params.size() ; i ++){
			q.setParameter(i+1, params.get(i));
		}
		q.setFirstResult(page.getOffset());
		q.setMaxResults(page.getPageSize());
		List<Object[]> results = q.getResultList();
		List<E> list = this.map(results, clazz);
		String countSql = "select count(1) from (" + sql + ") tableTemp";
		BigInteger count = (BigInteger) this.uniqueResultBySQLWithListParams(countSql, params);
		return new PageImpl<E>(list, page, count.longValue());
	}
	
	@Override
	public Object uniqueResultBySQLWithListParams(String sql, List<Object> params) {
		Query q = em.createNativeQuery(sql);
		for(int i = 0 ; i < params.size() ; i ++){
			q.setParameter(i+1, params.get(i));
		}
		return q.getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> queryPOJOBySQL(Class<E> clazz, String sql, Object...params){
		Query q = em.createNativeQuery(sql);
		for(int i = 0 ; i < params.length ; i ++){
			q.setParameter(i+1, params[i]);
		}
		return this.map(q.getResultList(), clazz);
	}

	/**
	 * 将Map转换成POJO
	 * @param objs
	 * @param targetClass
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	private <E> E assemble(Map<String, Object> objs, Class<E> targetClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		if (null == objs) {
			return null;
		}
		E target = targetClass.newInstance();
		if (target != null) {
			for(Map.Entry<String, Object> e : objs.entrySet()) {
				BeanUtils.copyProperty(target, e.getKey(), e.getValue());
			}
		}
		return target;
	}
	
	

	/**
	 * 讲对象数组转换成POJO
	 * @param objectArrayList
	 * @param genericType
	 * @return
	 */
	private <E> List<E> map(List<Object[]> objectArrayList, Class<E> genericType) {
        List<E> ret = new ArrayList<E>();
        List<Field> mappingFields = getNativeQueryResultColumnAnnotatedFields(genericType);
        try {
        	//DateLocaleConverter converter = new DateLocaleConverter();
            for (Object[] objectArr : objectArrayList) {
                E t = genericType.newInstance();
                for (int i = 0; i < objectArr.length; i++) {
					//ConvertUtils.register(converter, Date.class);
                    if(objectArr[i] != null){
                    	BeanUtils.setProperty(t, mappingFields.get(i).getName(), objectArr[i]);
                    }
                }
                ret.add(t);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            ret.clear();
        }
        return ret;
    }

    /**
     * Get ordered list of fields
     * @param genericType
     * @return
     */
    private <E> List<Field> getNativeQueryResultColumnAnnotatedFields(Class<E> genericType) {
        Field[] fields = genericType.getDeclaredFields();
        List<Field> orderedFields = Arrays.asList(new Field[fields.length]);
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(NativeQueryResultColumn.class)) {
                NativeQueryResultColumn nqrc = fields[i].getAnnotation(NativeQueryResultColumn.class);
                orderedFields.set(nqrc.index(), fields[i]);
            }
        }
        return orderedFields;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> queryArrBySQL(String sql, Object... params) {
		Query query = em.createNativeQuery(sql);
		for(int i = 0 ; i < params.length ; i ++){
			query.setParameter(i+1, params[i]);
		}
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> queryPOJOByProc(Class<E> clazz, String procName, List<SprocParamter> paramList) {
		StoredProcedureQuery query = em.createStoredProcedureQuery(procName);
		for(SprocParamter param : paramList){
			query.registerStoredProcedureParameter(param.getPosition(), param.getParamType(),param.getMode());
		}
		for(SprocParamter param : paramList){
			query.setParameter(param.getPosition(), param.getParamValue());
		}
		query.execute();
		return this.map(query.getResultList(), clazz);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> queryEntityByProc(Class<E> clazz, String procName, List<SprocParamter> paramList) {
		StoredProcedureQuery query =  em.createStoredProcedureQuery(procName,clazz);
		for(SprocParamter param :paramList ){
			query.registerStoredProcedureParameter(param.getPosition(), param.getParamType(),param.getMode());
		}
		for(SprocParamter param :paramList ){
			query.setParameter(param.getPosition(), param.getParamValue());
		}
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<List<?>> queryMultiResultSetByProc(List<Class<?>> clazzs, String procName, List<SprocParamter> paramList) {
		StoredProcedureQuery query =  em.createStoredProcedureQuery(procName);
		for(SprocParamter param :paramList ){
			query.registerStoredProcedureParameter(param.getPosition(), param.getParamType(),param.getMode());
		}
		for(SprocParamter param :paramList ){
			query.setParameter(param.getPosition(), param.getParamValue());
		}
		List<List<?>> multiResultSet = new ArrayList<>();
		boolean hasResult = false;
		int index = 0;
		do{
			List<Object[]> resultSet = query.getResultList();
			multiResultSet.add(this.map(resultSet, clazzs.get(index)));
			hasResult = query.hasMoreResults();
			index ++;
		}while(hasResult);
		return multiResultSet;
	}
	
}
