package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.request.UserLoginRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;
import com.doan.cv.error.InvalidValueException;
import com.doan.cv.service.UserService;
import com.doan.cv.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    JWTUtil jwtutil;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @APImessage("login successfully")
    public ResponseEntity<String> generateToken(@RequestBody @Valid UserLoginRequest userLoginRequest){
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        String accessToken = jwtutil.generateAccessToken(userLoginRequest.getEmail());
        String refreshToken = this.userService.updateUserRefreshToken(userLoginRequest.getEmail());

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                                                      .httpOnly(true)
                                                      .maxAge(6000)
                                                      .path("/")
                                                      .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(accessToken);
    }

    @GetMapping("/refresh")
    @APImessage("get refresh token")
    public ResponseEntity<String> getRefreshToken(@CookieValue(value = "refresh_token") String refreshToken) throws InvalidValueException {
        String email = this.jwtutil.extractUsername(refreshToken);

        if(!this.userService.validateUserRefreshToken(email, refreshToken))
            throw new InvalidValueException("Invalid refreshToken (expired refresh token)");

        String accessToken = jwtutil.generateAccessToken(email);
        String newRefreshToken = this.userService.updateUserRefreshToken(email);

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .maxAge(6000)
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(accessToken);
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

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", null)
                .httpOnly(true)
                .maxAge(0)
                .secure(true)
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();
    }
}
