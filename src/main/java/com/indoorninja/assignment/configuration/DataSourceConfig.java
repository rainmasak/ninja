package com.indoorninja.assignment.configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfig {

	@Resource
	Environment env;

	@Bean
	@Primary
	public DataSource dataSource2() {
		return DataSourceBuilder.create()
				.url(env.getProperty("spring.h2.url"))
				.driverClassName(env.getProperty("spring.h2.driverClassName"))
				.password(env.getProperty("spring.h2.password"))
				.username(env.getProperty("spring.h2.username"))
				.build();
	}
	
}
