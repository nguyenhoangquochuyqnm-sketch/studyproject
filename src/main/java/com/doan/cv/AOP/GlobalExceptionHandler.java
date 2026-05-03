package com.doan.cv.AOP;

import com.doan.cv.Entity.RestResponse;
import com.doan.cv.error.InvalidValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = InvalidValueException.class)
    public ResponseEntity<RestResponse<Void>> InvalidValueExceptionHandler(InvalidValueException invalidValueException){
        RestResponse<Void> res = new RestResponse<>();

        res.setMessage(invalidValueException.getMessage());
        res.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(res);
    }
}
