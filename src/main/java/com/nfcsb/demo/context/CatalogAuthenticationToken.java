package com.nfcsb.demo.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Dummy implementation ... a context ..
 */
public class CatalogAuthenticationToken implements Authentication {

	private final String token;

	// some user ... when logged in
	private final String user;

	public CatalogAuthenticationToken(String token, String user) { // todo ... this will be the RequestContext ...
		this.token = token;
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		 List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		 grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		 return grantedAuthorities;
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
