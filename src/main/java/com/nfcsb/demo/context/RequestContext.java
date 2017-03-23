package com.nfcsb.demo.context;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 */
public interface RequestContext {

	Map<String, String> getHeaders(HttpServletRequest servletRequest);

	String getScheme();

	String getDomain();

	int getPort();

	String getClientIP();

	String getHeader(String name);

	Map<String, String> getHeaders();

	Map<String, String> getQuery();

	String getPath();
}
