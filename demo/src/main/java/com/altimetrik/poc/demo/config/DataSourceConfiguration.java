/*package com.altimetrik.poc.demo.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfiguration implements EnvironmentAware {

	@Value("${spring.datasource.hikari.jdbc-url:jdbc:h2:mem:testdb}")
	private String url;

	@Value("${spring.datasource.hikari.username:sa}")
	private String userName;

	@Value("${spring.datasource.hikari.password:}")
	private String password;

	@Value("${spring.datasource.hikari.driver-class-name:org.h2.Driver}")
	private String className;

	@Value("${spring.datasource.hikari.pool-name:MyDbPool}")
	private String poolName;

	@Value("${spring.datasource.hikari.connection-timeout:60000}")
	private int connTimeOut;

	@Value("${spring.datasource.hikari.maximum-pool-size:5}")
	private int poolSize;

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

	private Environment environment;

	@Bean(destroyMethod = "shutdown")
	public DataSource datasource() {
		HikariConfig config = new HikariConfig();
		
		config.setDataSourceClassName(className);
		config.addDataSourceProperty("user", userName);
		config.addDataSourceProperty("password", password);
		config.addDataSourceProperty("url", url);
		config.setMaximumPoolSize(poolSize);
		config.setPoolName(poolName);
	//	config.setMetricRegistry(metricRegistry);
		LOGGER.info("Loading Hikari Pool ........");
		return new HikariDataSource(config);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
*/