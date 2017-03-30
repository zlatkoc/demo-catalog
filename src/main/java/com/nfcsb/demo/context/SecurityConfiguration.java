package com.nfcsb.demo.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


/**
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	//private final CatalogAuthenticationFilter2 authenticationFilter;

	private final CatalogAuthenticationFilter catalogFilter;

	@Autowired
	public SecurityConfiguration(CatalogAuthenticationFilter filter) {

		super();
		catalogFilter = filter;
		//	authenticationFilter = other;
		//catalogAuthenticationProvider = provider;
	}

	/*@Bean
	public DispatcherServlet myDispatcherServlet(ApplicationContext applicationContext) {

		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.setParent(applicationContext);


		DispatcherServlet dispatcher = new DispatcherServlet(webContext);
		dispatcher.setThrowExceptionIfNoHandlerFound(true);
		return dispatcher;
	}
*/
	@Override
	public void configure(WebSecurity web) {

		// no security what so ever for this part ...
		web.ignoring()
			.antMatchers("/swagger-ui.html")
			.and().ignoring().antMatchers("swagger-ui.html/**");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Let all request past this point ... for all RESTs
		// except when they have a @PreAuthorize annotation present
		http.authorizeRequests()
			.antMatchers("/**")
			.permitAll().and()
			.addFilterBefore(catalogFilter, BasicAuthenticationFilter.class);
		//.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class);

	}
}
