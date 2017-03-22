package com.nfcsb.demo.catalog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    //@ExceptionHandler()
    public ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        return handleExceptionInternal(e, "Error", null, HttpStatus.BAD_GATEWAY, request);
    }
}