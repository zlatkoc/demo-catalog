package com.nfcsb.demo.context;

import com.nfcsb.demo.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */

@Component
public class CatalogAuthenticationFilter2 extends AbstractAuthenticationProcessingFilter {
	private final Logger log = LoggerFactory.getLogger(getClass());

	//@Autowired
	SessionService sessions;

	@Autowired
	public CatalogAuthenticationFilter2(SessionService sessionService) {

		super(new AntPathRequestMatcher("/**"));
		sessions = sessionService;
	}

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		// NOTE: delete when provider provide roles
		List<? extends GrantedAuthority> authorities = Collections
			.unmodifiableList(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

		// RequestContext principal = new RequestContextImpl(request);

		// NOTE: set credentials when provider provides them
		String xAuth = request.getHeader("X-Token");
		String user = sessions.get(xAuth);

		if (user == null) {
			return null;
		}

		// validate the value in xAuth
		/*if (user == null) {
			log.info("No X-Token provided ... unauthenticated request!");
			SecurityContextHolder.getContext().setAuthentication(null);
			filterChain.doFilter(request, response);
			return;
		}*/

		log.info("Filling up authentication: " + xAuth);

		// Fill up authentication
		return new CatalogAuthenticationToken(user, request);
	}

	@Override
	protected void successfulAuthentication( HttpServletRequest request,
	                                         HttpServletResponse response,
	                                         FilterChain chain,
	                                         Authentication authResult )
		throws IOException, ServletException {
		if ( logger.isDebugEnabled() ) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

		/*if ( this.eventPublisher != null ) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}*/

		chain.doFilter(request, response);
	}
}
