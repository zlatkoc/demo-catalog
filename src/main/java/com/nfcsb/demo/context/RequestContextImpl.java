package com.nfcsb.demo.context;

import com.zandero.http.RequestUtils;
import com.zandero.utils.StringUtils;
import com.zandero.utils.UrlUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RequestScope
public class RequestContextImpl implements RequestContext {

	private final int port;

	private final String scheme;

	private final String domain;

	private final Map<String, String> headers;

	private final Map<String, String> query;

	private final String path;

	private final String clientIpAddress;

	private final CatalogAuthenticationToken token;

	public RequestContextImpl() {

		this(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
	}

	public RequestContextImpl(HttpServletRequest request) {
		scheme = RequestUtils.getScheme(request);
		port = request.getServerPort();

		domain = UrlUtils.resolveDomain(request.getServerName());
		path = request.getServletPath();

		clientIpAddress = RequestUtils.getClientIpAddress(request);
		headers = getHeaders(request);

		if (StringUtils.isNullOrEmptyTrimmed(request.getQueryString())) {
			query = null;
		}
		else{
			query = UrlUtils.getQuery(request.getQueryString());
		}

		// resolve authentication if any
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof CatalogAuthenticationToken) {
			token = (CatalogAuthenticationToken)authentication;
		}
		else {
			token = null;
		}
	}

	@Override
	public Map<String, String> getHeaders(HttpServletRequest servletRequest) {

		Map<String, String> map = new HashMap<>();
		Enumeration e = servletRequest.getHeaderNames();

		if (e != null) {
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = servletRequest.getHeader(name);

				map.put(name, value);
			}
		}

		return map;
	}

	@Override
	public String getScheme() {

		return scheme;
	}

	@Override
	public String getDomain() {

		return domain;
	}

	@Override
	public int getPort() {

		return port;
	}

	@Override
	public String getClientIP() {

		return clientIpAddress;
	}

	@Override
	public String getHeader(String name) {

		if (headers == null) {
			return null;
		}

		return headers.get(name);
	}

	@Override
	public Map<String, String> getHeaders() {

		return headers;
	}

	@Override
	public Map<String, String> getQuery() {

		return query;
	}

	@Override
	public String getPath() {

		return path;
	}

	public String getUser() {

		return token != null ? token.getUser() : null;
	}
}
