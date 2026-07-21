package com.doan.cv.controller;


import com.doan.cv.dto.request.UserCreateRequest;
import com.doan.cv.dto.request.UserUpdateRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getALlUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(userService.createUser(userCreateRequest));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest){
        UserResponse updatedUser = userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
