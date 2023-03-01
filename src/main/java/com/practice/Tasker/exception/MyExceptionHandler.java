package com.practice.Tasker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

   @ExceptionHandler
    public ResponseEntity<ErrorMessage> existException(ExistException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> forbiddenException(ForbiddenException ex){
       return ResponseEntity.status(HttpStatus.FORBIDDEN)
               .body(new ErrorMessage(ex.getMessage()));
    }
}
