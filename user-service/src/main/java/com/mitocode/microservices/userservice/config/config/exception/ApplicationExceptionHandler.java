package com.mitocode.microservices.userservice.config.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Map<String, Object>> userEntityException(NullPointerException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bad Request");
        response.put("responseCode", 400);
        response.put("errorDetails", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}