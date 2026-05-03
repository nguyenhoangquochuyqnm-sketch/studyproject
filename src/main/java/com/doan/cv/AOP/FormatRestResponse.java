package com.doan.cv.AOP;

import com.doan.cv.Entity.RestResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;

@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object>{
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse= ((ServletServerHttpResponse)response).getServletResponse();
        int statusCode = servletResponse.getStatus();

        if(body instanceof RestResponse)
            return body;

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(statusCode);

        if(statusCode >= 400){
            res.setError("INVALID VALUE ERROR");
            res.setMessage(body);
        } else{
            res.setData(body);
            res.setMessage("OK CON DE");
        }

        return res;
    }
}
