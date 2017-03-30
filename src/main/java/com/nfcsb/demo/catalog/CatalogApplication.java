package com.nfcsb.demo.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.nfcsb.demo"})
@SpringBootApplication
public class CatalogApplication {

	public static void main(String[] args) {

	//	ConfigurableApplicationContext context =
		SpringApplication.run(CatalogApplication.class, args);


		/*DispatcherServlet dispatcher = new DispatcherServlet((WebApplicationContext) context);
		dispatcher.setThrowExceptionIfNoHandlerFound(true);*/
	}

	/*@Bean
	public DispatcherServlet dispatcherServlet() {

		DispatcherServlet servlet = new DispatcherServlet();
		servlet.setThrowExceptionIfNoHandlerFound(true);
		return servlet;
	}*/

	/*@Bean
	public ServletRegistrationBean dispatchServletRegistration() {

		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/");

		registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		return registration;

	}*/
}
