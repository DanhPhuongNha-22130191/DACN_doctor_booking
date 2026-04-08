package com.example.doctorbooking.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<Map<String, Object>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        Map<String, Object> response = new HashMap<>();
        response.put("code", errorCode.getCode());
        response.put("message", errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<Map<String, Object>> handlingRuntimeException(Exception exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.put("message", ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode()).body(response);
    }
}
