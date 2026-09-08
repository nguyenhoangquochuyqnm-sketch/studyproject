package com.doan.cv.aop;


import com.doan.cv.annotation.APImessage;
import com.doan.cv.entity.RestResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class FormatRestResponse{
    @Around("within(@org.springframework.web.bind.annotation.RestController *) " +
            "&& !within(@org.springframework.web.bind.annotation.RestControllerAdvice *) ")
    public Object formatResponse(ProceedingJoinPoint joinPoint) throws Throwable {

        ResponseEntity<?> result = (ResponseEntity<?>)joinPoint.proceed();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        APImessage apiMessage = method.getAnnotation(APImessage.class);
        String message = (apiMessage != null) ? apiMessage.value() : "SUCCESS";

        RestResponse<Object> response = new RestResponse<>();

        response.setData(result.getBody());
        response.setMessage(message);
        response.setStatusCode(result.getStatusCode().value());

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(result.getHeaders())
                .body(response);
    }
}
