package com.witbooking.redis.config;

import com.witbooking.redis.core.exception.RedisException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RedisHandlerException {

    @ExceptionHandler(value = {RedisException.class, IllegalArgumentException.class})
    protected ResponseEntity<String> handleBadRequest(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    protected ResponseEntity<String> handleMethodArgumentNotValidException(MissingServletRequestParameterException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        final String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
