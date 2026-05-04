package com.doan.cv.controller;

import com.doan.cv.dto.request.UserRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    public String generateToken(@RequestBody UserRequest userRequest){

    }

}
