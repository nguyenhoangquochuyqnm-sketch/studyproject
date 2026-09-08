package com.doan.cv.aop;

import com.doan.cv.entity.RestResponse;
import com.doan.cv.error.DuplicateValueException;
import com.doan.cv.error.InvalidValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {InvalidValueException.class, DuplicateValueException.class})
    public ResponseEntity<RestResponse<Void>> ValueExceptionHandler(RuntimeException e) {
        RestResponse<Void> res = new RestResponse<>();

        res.setMessage("Invalid value... ");
        res.setError(e.getMessage());
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Map<String, String>>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        RestResponse<Map<String, String>> res = new RestResponse<>();

        res.setMessage("Validation failed");
        res.setError("missing required argument");
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errorMap.put(fieldName, message);
        });

        res.setData(errorMap);

        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse<Void>> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        RestResponse<Void> res = new RestResponse<>();

        res.setMessage("Malformed JSON request or invalid data type format");
        res.setError(e.getMessage());
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = MissingRequestCookieException.class)
    public ResponseEntity<RestResponse<Void>> MissingRequestCookieExceptionHandler(MissingRequestCookieException e){
        RestResponse<Void> response = new RestResponse<>();

        response.setMessage("Missing required cookie");
        response.setError(e.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(response);
    }
}