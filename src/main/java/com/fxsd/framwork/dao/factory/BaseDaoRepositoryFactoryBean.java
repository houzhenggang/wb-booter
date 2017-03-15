package com.fxsd.framwork.dao.factory;

import com.fxsd.framwork.dao.BaseDao;
import com.fxsd.framwork.dao.impl.BaseDaoImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 定义 RepositoryFactoryBean 的实现类
 * 
 * @author ChenJianhui
 * @param <R>
 * @param <T>
 * @param <ID>
 */
public class BaseDaoRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, ID> {

	public BaseDaoRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new BaseDaoFactory(entityManager);
	}

	/**
	 * 自定义DaoFactory类
	 * 
	 * @author ChenJianhui
	 * @param <S>
	 * @param <ID>
	 */
	private static class BaseDaoFactory<S, ID extends Serializable> extends JpaRepositoryFactory {
		public BaseDaoFactory(EntityManager entityManager) {
			super(entityManager);
		}

		@Override
		protected <T, ID2 extends Serializable> org.springframework.data.jpa.repository.support.SimpleJpaRepository<?, ?> getTargetRepository(
				RepositoryInformation information, EntityManager entityManager) {
			return new BaseDaoImpl<>(information.getDomainType(), entityManager);

		};

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return BaseDao.class;
		}
	}
}
