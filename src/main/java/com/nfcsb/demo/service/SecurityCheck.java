package com.nfcsb.demo.service;

import com.nfcsb.demo.context.CatalogAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Check security via method
 */
@Component
public class SecurityCheck {

	private static final Logger log = LoggerFactory.getLogger(SecurityCheck.class);

	public boolean hasToken(Authentication authentication) {

		if (authentication instanceof CatalogAuthenticationToken) {

			CatalogAuthenticationToken token = (CatalogAuthenticationToken) authentication;
			log.info("User logged in: " + token.getUser());

			return token.isAuthenticated();
		}

		// no user not granted
		return false;
	}
}
