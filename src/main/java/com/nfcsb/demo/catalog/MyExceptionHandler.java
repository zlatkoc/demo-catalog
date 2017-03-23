package com.nfcsb.demo.catalog;

import com.nfcsb.demo.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

   /* @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        return handleExceptionInternal(e, "Error", null, HttpStatus.BAD_GATEWAY, request);
    }*/


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

        ErrorResponse errorResponse = new ErrorResponse(ex, statusCode);
        return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("path",request.getContextPath());
        responseBody.put("message","The URL you have reached is not in service at this time (404).");
        return new ResponseEntity<Object>(responseBody,HttpStatus.NOT_FOUND);
    }
}