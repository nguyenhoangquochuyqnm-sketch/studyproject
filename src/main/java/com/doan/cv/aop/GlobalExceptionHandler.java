package com.doan.cv.aop;

import com.doan.cv.entity.RestResponse;
import com.doan.cv.error.InvalidValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Map<String, String>>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){

        RestResponse<Map<String,String>> response = new RestResponse<>();

        response.setMessage(e.getMessage());
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach( error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errorMap.put(fieldName,message);
        });

        response.setData(errorMap);

        return ResponseEntity.badRequest().body(response);
    }
}
