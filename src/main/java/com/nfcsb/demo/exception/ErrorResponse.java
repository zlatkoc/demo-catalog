package com.nfcsb.demo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 *
 */
@Data
public class ErrorResponse {

	public final String message;

	public final int code;

	public ErrorResponse(Throwable ex, HttpStatus statusCode) {

		message = ex.getMessage();
		code = statusCode.value();
	}
}
