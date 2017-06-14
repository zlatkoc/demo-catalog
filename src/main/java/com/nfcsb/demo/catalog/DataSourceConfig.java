package com.nfcsb.demo.catalog;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
@Component
public class DataSourceConfig {

	/*spring.jpa.database=POSTGRESQL
	spring.datasource.platform=postgres
	spring.jpa.show-sql=true
	spring.jpa.hibernate.ddl-auto=create-drop
	spring.database.driverClassName=org.postgresql.Driver
	spring.datasource.url=jdbc:postgresql://localhost:5432/test
	spring.datasource.username=drejc
	spring.datasource.password=*/

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		DataSourceBuilder builder = DataSourceBuilder
				.create()
				.username("drejc")
				.password("")
				.url("jdbc:postgresql://localhost:5432/test");
		//.driverClassName("org.postgresql.Driver");

		return builder.build();
	}

	/*@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/test");
		dataSource.setUsername("drejc");
		dataSource.setPassword("");

		return dataSource;
	}*/

	// Transaction manager bean definition
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}
}
