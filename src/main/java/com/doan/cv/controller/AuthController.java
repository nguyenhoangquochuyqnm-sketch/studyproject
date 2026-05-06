package com.doan.cv.controller;

import com.doan.cv.dto.request.UserRequest;
import com.doan.cv.util.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JWTutil jwtutil;

    @PostMapping
    public String generateToken(@RequestBody UserRequest userRequest) throws Exception{
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getName(),userRequest.getPassword()));

        return jwtutil.generateToken(userRequest.getName());
    }

}
