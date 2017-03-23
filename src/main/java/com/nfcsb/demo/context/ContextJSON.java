package com.nfcsb.demo.context;

import lombok.Data;

/**
 *
 */
@Data
public class ContextJSON {

	public final String domain;

	public final int port;

	public final String path;

	public ContextJSON(RequestContextImpl context) {

		domain = context.getDomain();
		port = context.getPort();
		path = context.getPath();
	}
}
