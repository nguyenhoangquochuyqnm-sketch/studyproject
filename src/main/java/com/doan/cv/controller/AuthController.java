package com.doan.cv.controller;

import com.doan.cv.dto.request.UserRequest;
import com.doan.cv.util.JWTutil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<String> generateToken(@RequestBody @Valid UserRequest userRequest){
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getName(),userRequest.getPassword()));

        String token = jwtutil.generateToken(userRequest.getName());
        return ResponseEntity.ok(token);
    }

}
