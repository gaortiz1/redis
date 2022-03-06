package com.witbooking.redis.config;

import com.witbooking.redis.core.exception.RedisException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RedisHandlerException {

    @ExceptionHandler(value = {RedisException.class, IllegalArgumentException.class})
    protected ResponseEntity<String> handleConflict(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
