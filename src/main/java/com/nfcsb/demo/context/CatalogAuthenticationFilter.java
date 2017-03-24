package com.nfcsb.demo.context;

import com.nfcsb.demo.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Component
public class CatalogAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(CatalogAuthenticationFilter.class);

	SessionService sessions;

	@Autowired
	public CatalogAuthenticationFilter(SessionService sessionService) {

		sessions = sessionService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String xAuth = request.getHeader("X-Token");
		String user = sessions.get(xAuth);

		// validate the value in xAuth
		if (user == null) {
			log.info("No X-Token provided ... unauthenticated request!");
			SecurityContextHolder.getContext().setAuthentication(null);
			filterChain.doFilter(request, response);
			return;
		}

		log.info("Filling up authentication: " + xAuth);
		// Fill up authentication
		Authentication auth = new CatalogAuthenticationToken(xAuth, user);
		SecurityContextHolder.getContext().setAuthentication(auth);

		filterChain.doFilter(request, response);
	}

}