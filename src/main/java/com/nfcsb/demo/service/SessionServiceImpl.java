package com.nfcsb.demo.service;

import com.zandero.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SessionServiceImpl implements SessionService {

	@Override
	public String get(String token) {

		// find session by token
		if (StringUtils.isNullOrEmptyTrimmed(token)) {
			return null;
		}

		// return dummy username
		return token + "_user";
	}
}
