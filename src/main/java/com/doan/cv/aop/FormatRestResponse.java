package com.doan.cv.aop;


import com.doan.cv.entity.RestResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FormatRestResponse{
    @Around("within(@org.springframework.web.bind.annotation.RestController *) " +
            "&& !within(@org.springframework.web.bind.annotation.RestControllerAdvice *) " +
            "&& !within(com.doan.cv.controller.AuthController)")
    public Object formatResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseEntity<?> result = (ResponseEntity<?>) joinPoint.proceed();

        RestResponse<Object> response = new RestResponse<>();

        response.setData(result.getBody());
        response.setMessage("SUCCESS");
        response.setStatusCode(result.getStatusCode().value());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
