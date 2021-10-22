package com.example.auth.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Response> handleRoleNotFoundException(RoleNotFoundException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
