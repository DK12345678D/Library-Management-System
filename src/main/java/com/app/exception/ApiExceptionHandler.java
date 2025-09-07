package com.app.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> notFound(ResourceNotFoundException ex, HttpServletRequest req){
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", ex.getMessage(), "timestamp", Instant.now().toString(), "path", req.getRequestURI()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> all(Exception ex, HttpServletRequest req){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("error", ex.getMessage(), "timestamp", Instant.now().toString(), "path", req.getRequestURI()));
  }
}

