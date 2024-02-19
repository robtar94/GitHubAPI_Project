package com.example.githubapi_project.users.controller;


import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UsersControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {FeignException.NotFound.class})
    protected ResponseEntity<HttpError> handleFeign404() {
        return new ResponseEntity<>(HttpError.builder()
                .status("404")
                .message("Specified user has been not found")
                .build(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
