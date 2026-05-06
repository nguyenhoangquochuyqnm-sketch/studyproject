package com.doan.cv.util;

import com.doan.cv.dto.request.UserRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        return user;
    }

    public UserResponse toResponse(User user){
        UserResponse response = new UserResponse();

        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());

        return response;
    }
}
