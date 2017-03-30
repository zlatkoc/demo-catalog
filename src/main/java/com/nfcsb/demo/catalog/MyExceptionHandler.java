package com.nfcsb.demo.catalog;

import com.nfcsb.demo.context.RequestContextImpl;
import com.nfcsb.demo.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Custom exception handler
 */
@Controller
@ControllerAdvice
@RestController
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * If not other REST is invoked ... we return an error response for all other calls
	 * @param context request
	 * @return 404 path not found response
	 */
	@RequestMapping("/**")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handlerNotMappingRequest(RequestContextImpl context) {
		return new ErrorResponse("Path not found: '" + context.getPath() +"'", HttpStatus.NOT_FOUND);
	}

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

	/*@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handleNoHandlerFoundException(NoHandlerFoundException ex) {
		logger.error("ErrorLog: ", ex);
		return new ModelAndView("error/exception", "exceptionMsg", "NoHandlerFoundException msg: " + ex.toString());
	}*/

	//@ExceptionHandler(NoHandlerFoundException.class)
	/*@ResponseStatus(HttpStatus.NOT_FOUND)
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
	                                                               HttpHeaders headers,
	                                                               HttpStatus status,
	                                                               WebRequest request) {

		logger.error("*****");
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		ErrorResponse errorResponse = new ErrorResponse("Path not found: " + servletRequest.getServletPath(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}*/
/*
	@ResponseStatus(HttpStatus.NOT_FOUND)
	//@ExceptionHandler(MyBadDataException.class)
	@ResponseBody
	*//*ErrorResponse handleBadRequest(HttpServletRequest req, Exception ex) {
		return new ErrorResponse("NE najdem", HttpStatus.NOT_FOUND);
	}*//*

	protected ResponseEntity<Object> handleNoHandlerFoundException(
		NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}*/
}