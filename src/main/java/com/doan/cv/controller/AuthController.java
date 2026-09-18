package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.request.UserCreateRequest;
import com.doan.cv.dto.request.UserLoginRequest;
import com.doan.cv.dto.response.AuthResponse;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.error.InvalidValueException;
import com.doan.cv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @APImessage("create a new account")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userCreateRequest));
    }

    @PostMapping("/login")
    @APImessage("login successfully")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody @Valid UserLoginRequest userLoginRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        String refreshToken = this.userService.updateUserRefreshToken(userLoginRequest.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, AuthController.getCookie(refreshToken))
                .body(this.userService.generateAuthResponse(userLoginRequest.getEmail()));
    }

    @GetMapping("/refresh")
    @APImessage("get refresh token")
    public ResponseEntity<AuthResponse> getRefreshToken(@CookieValue(value = "refresh_token") String refreshToken) throws InvalidValueException {
        String email = this.userService.validateUserRefreshToken(refreshToken);

        String newRefreshToken = this.userService.updateUserRefreshToken(email);

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, AuthController.getCookie(newRefreshToken))
                             .body(this.userService.generateAuthResponse(email));
    }

    @GetMapping("/account")
    @APImessage("fetch account")
    public ResponseEntity<UserResponse> getUserAccount(){
        return ResponseEntity.ok(this.userService.getCurrentUserLogin());
    }

    @PostMapping("/logout")
    @APImessage("logout successfully")
    public ResponseEntity<Void> logOut(){
        this.userService.logoutUser();
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, AuthController.getCookie(null))
                             .build();
    }

    private static String getCookie(String cookieValue) {
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", cookieValue)
                .httpOnly(true)
                .maxAge(0)
                .secure(true)
                .path("/")
                .build();
        return responseCookie.toString();
    }
}
