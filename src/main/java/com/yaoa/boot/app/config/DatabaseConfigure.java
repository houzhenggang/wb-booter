package com.yaoa.boot.app.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.yaoa.boot.app.dao.factory.BaseDaoRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * AppConfig
 * 
 * @author cjh
 * @version
 * @date：2016年3月8日 下午12:09:35
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {"${jpa.dao.packages}"}, repositoryFactoryBeanClass = BaseDaoRepositoryFactoryBean.class)
public class DatabaseConfigure {

	@Autowired
    private Environment env;
	
	/**
	 * 配置数据源
	 * @return
	 */
	@Bean
	public DataSource dataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("db.jdbc-url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		dataSource.setInitialSize(Integer.parseInt(env.getProperty("db.init-size")));
		dataSource.setMinIdle(Integer.parseInt(env.getProperty("db.min-idle")));
		dataSource.setMaxActive(Integer.parseInt(env.getProperty("db.max-active")));
		dataSource.setMaxWait(Integer.parseInt(env.getProperty("db.max-wait")));
		dataSource.setValidationQuery("SELECT 'X'");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(false);
		dataSource.setTestWhileIdle(true);
		return dataSource;
	}
	
	/**
	 * 配置 EntityManagerFactory
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPackagesToScan(env.getProperty("jpa.entity.packages"));
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.show_sql", env.getProperty("jpa.show-sql"));
		jpaProperties.setProperty("hibernate.format_sql",  env.getProperty("jpa.format-sql"));
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("jpa.ddl-auto"));
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaProperties.setProperty("hibernate.jdbc.fetch_size", "50");
		jpaProperties.setProperty("hibernate.jdbc.batch_size", "50");
		jpaProperties.setProperty("javax.persistence.validation.mode", "none");
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory;
	}
	
	/**
	 * 配置事务管理器
	 * @return
	 */
	@Bean
	public JpaTransactionManager transactionManager(){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
		return transactionManager;
	}


}
