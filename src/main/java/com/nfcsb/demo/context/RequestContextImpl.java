package com.nfcsb.demo.context;

//import com.zandero.http.RequestUtils;
import com.zandero.utils.UrlUtils;

import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
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

	private final String query;

	private final String path;

	private final String clientIpAddress;

	public RequestContextImpl() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		scheme = getScheme(request);
		port = request.getServerPort();

		domain = UrlUtils.resolveDomain(request.getServerName());
		path = request.getServletPath();

		clientIpAddress = getClientIpAddress(request);
		headers = getHeaders(request);
		query = request.getQueryString();
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
	public String getQuery() {

		return query;
	}

	@Override
	public String getPath() {

		return path;
	}


	/**
	 * see: http://stackoverflow.com/questions/19598690/how-to-get-host-name-with-port-from-a-http-or-https-request/19599143#19599143
	 * takes into account that request might go through a reverse proxy and request.getScheme() might not be correct
	 *
	 * @param request request
	 * @return scheme (http, https ...)
	 */
	private static String getScheme(HttpServletRequest request) {

		String scheme = request.getHeader("X-Forwarded-Proto");
		if (scheme == null || scheme.trim().length() == 0) {
			scheme = request.getScheme();
		}

		return scheme != null ? scheme.trim().toLowerCase() : null;
	}

	/**
	 * Make sure that client not internal ip address is returned
	 *
	 * @param request to look up all possible headers
	 * @return ip address
	 */
	private static String getClientIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * Resolves domain name from given URL
	 *
	 * @param url to get domain name from
	 * @return domain name or null if not resolved
	 */
	private static String resolveDomain(String url) {

		if (url == null || url.trim().length() == 0) {
			return null;
		}

		// check if url starts with scheme part (http:// ... ) if not add one manually
		if (!url.contains("://")) {
			url = "http://" + url;
		}

		try {
			URI uri = new URI(url);
			return uri.getHost();
		}
		catch (URISyntaxException e) {
			return null;
		}
	}
}
