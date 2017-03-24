package com.nfcsb.demo.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.nfcsb.demo"})
@SpringBootApplication
public class CatalogApplication {

	public static void main(String[] args) {

		SpringApplication.run(CatalogApplication.class, args);
	}
}
