package com.doan.cv.mapper;

import com.doan.cv.dto.request.UserCreateRequest;
import com.doan.cv.dto.request.UserUpdateRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createToEntity(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(this.passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setAge(userCreateRequest.getAge());
        user.setAddress(userCreateRequest.getAddress());
        user.setGender(userCreateRequest.getGender());

        return user;
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();

        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setAge(user.getAge());
        response.setAddress(user.getAddress());
        response.setGender(user.getGender());

        return response;
    }

    public void updateToEntity(UserUpdateRequest source, User target){
        target.setName(source.getName());
        target.setAge(source.getAge());
        target.setAddress(source.getAddress());
        target.setGender(source.getGender());
    }
}