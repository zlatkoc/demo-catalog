package com.nfcsb.demo.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

/**
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContextJSON {

	public final String domain;

	public final int port;

	public final String path;

	public final Map<String, String> query;

	public ContextJSON(RequestContext context) {

		domain = context.getDomain();
		port = context.getPort();
		path = context.getPath();

		query = context.getQuery();
	}
}
