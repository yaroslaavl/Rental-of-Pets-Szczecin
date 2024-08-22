package org.yaroslaavl.webappstarter.http.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerRest {

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<String> handleHttpMessageNotWritableException(HttpMessageNotWritableException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Format error: " + ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Media type is: " + ex.getMessage());
    }

}
