package com.fal.springboot.config.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class MybatisDataSourceConfig {

	@Autowired
	private DataSource dataSource;
	
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(resolver.getResources("classpath:com/fal/springboot/mapping/*.xml"));
			SqlSessionFactory sqlSessionFactory = bean.getObject();
			sqlSessionFactory.getConfiguration().setCacheEnabled(Boolean.TRUE);
			return sqlSessionFactory;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
