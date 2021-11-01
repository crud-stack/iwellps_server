package com.iwell.eye.common.config;

import com.iwell.eye.common.interceptor.SqlLoggingInterceptor;
import com.iwell.eye.common.util.ClassFinderUtilForModelAlias;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@EnableTransactionManagement
@Configuration
@Component
@MapperScan(value = { "com.iwell.eye.**" })
public class DataConfig {

	@Autowired
	private Environment env;


	public DataConfig() {
		System.out.println("┌──────────────────────────────");
		System.out.println("│ Doing DataConfig ...");
		System.out.println("└──────────────────────────────");
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		String jndiName = env.getProperty("spring.datasource.jndiName");
		if (jndiName == null) {
			System.out.println("┌──────────────────────────────");
			System.out.println("│ Set DBCP");
			System.out.println("└──────────────────────────────");
			HikariDataSource dataSource = new HikariDataSource();
			dataSource.setMaximumPoolSize(Integer.valueOf(env.getProperty("spring.datasource.max.pool.size")));
			dataSource.setDataSourceClassName(env.getProperty("spring.datasource.driver-class-name"));
			dataSource.addDataSourceProperty("url", env.getProperty("spring.datasource.url"));
			dataSource.addDataSourceProperty("user", env.getProperty("spring.datasource.username"));
			dataSource.addDataSourceProperty("password", env.getProperty("spring.datasource.password"));


			return dataSource;
		} else {
			System.out.println("┌──────────────────────────────");
			System.out.println("│ Set JNDI :: " + jndiName);
			System.out.println("└──────────────────────────────");
			JndiDataSourceLookup lookup = new JndiDataSourceLookup();
			return lookup.getDataSource(jndiName);
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException, ClassNotFoundException {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());

		/* set Interceptor */
		SqlLoggingInterceptor sqlLoggingInterceptor = sqlLoggingInterceptor();
		sessionFactory.setPlugins(new Interceptor[] { sqlLoggingInterceptor });

		/* MyBatis settings values */
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
		// lazyLoadingEnabled: true
		// aggressiveLazyLoading: false
		// mapUnderscoreToCamelCase: true
		// jdbc-type-for-null: NULL
		// default-fetch-size: 100
		// default-statement-timeout: 30
		configuration.setCacheEnabled(false);
		configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setMapUnderscoreToCamelCase(true);
		sessionFactory.setConfiguration(configuration);
//		configuration.setMapUnderscoreToCamelCase(true);
//		sessionFactory.setConfiguration(configuration);
		
		/* SqlMap loader warn setMapperLocations 확인필요 */
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml");
		sessionFactory.setMapperLocations(res);
		sessionFactory.setTypeAliases(ClassFinderUtilForModelAlias.getClasses("com.iwell.eye"));

		return sessionFactory;
	}

	@Bean
	public SqlLoggingInterceptor sqlLoggingInterceptor() {
		return new SqlLoggingInterceptor();
	}

}