package com.nfcsb.demo.catalog;

import com.nfcsb.demo.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Custom exception handler
 */
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {

		HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		if (ex instanceof IllegalArgumentException) {
			statusCode = HttpStatus.BAD_REQUEST;
		}

		if (ex instanceof IllegalAccessException) {
			statusCode = HttpStatus.FORBIDDEN;
		}

		if (ex instanceof IOException) {
			statusCode = HttpStatus.METHOD_NOT_ALLOWED;
		}

		if (ex instanceof AccessDeniedException) {
			statusCode = HttpStatus.UNAUTHORIZED; // maybe 401
		}

		logger.error(ex.getMessage(), ex);

		ErrorResponse errorResponse = new ErrorResponse(ex, statusCode);
		return new ResponseEntity<>(errorResponse, statusCode);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		ErrorResponse errorResponse = new ErrorResponse(servletRequest.getServletPath(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}