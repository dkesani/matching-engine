package com.dev.exercices.app.exception;


import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dev.exercices.dto.OrderError;

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity handleConstraintExceptions(ConstraintViolationException exc) {
    OrderError error = new OrderError.Builder().message(exc.getMessage()).statusCode(HttpStatus.BAD_REQUEST).build();
    return new ResponseEntity(error, error.statusCode());
  }
}
