package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.request.UserCreateRequest;
import com.doan.cv.dto.request.UserUpdateRequest;
import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @APImessage("fetch all users")
    public ResponseEntity<ResultPagination<List<UserResponse>>> getAllUsers(@RequestParam(name = "current", defaultValue = "1") int currentPage,
                                                                            @RequestParam(name = "size", defaultValue = "10") int pageSize){
        if(currentPage < 1)
            currentPage = 1;
        if (pageSize < 1 || pageSize > 100)
            pageSize = 10;

        Pageable pageable = PageRequest.of(currentPage - 1 , pageSize);

        return ResponseEntity.ok().body(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    @APImessage("fetch user by id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping
    @APImessage("create a user")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userCreateRequest));
    }

    @PutMapping
    @APImessage("update a user")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest){
        UserResponse updatedUser = userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @APImessage("delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}