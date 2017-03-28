package com.nfcsb.demo.context;

import com.zandero.http.RequestUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

/**
 * Dummy implementation ... a context ..
 */
public class CatalogAuthenticationToken implements Authentication {

	private final String token;

	// some user ... when logged in
	private final String user;

	public CatalogAuthenticationToken(String user, String token) { // todo ... this will be the RequestContext ...
		this.token = token;
		this.user = user;
	}

	public CatalogAuthenticationToken(String user, HttpServletRequest request) { // todo ... this will be the RequestContext ...
		this.token = RequestUtils.getHeader(request, "X-Token");
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")); // fill up role that is checked in @PreAuthorization annotation
	}

	@Override
	public Object getCredentials() {

		return null;
	}

	@Override
	public Object getDetails() {

		return null;
	}

	@Override
	public Object getPrincipal() {

		return null;
	}

	@Override
	public boolean isAuthenticated() {

		return token != null;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public String getName() {

		return null;
	}

	public String getUser() {

		return user;
	}
}
