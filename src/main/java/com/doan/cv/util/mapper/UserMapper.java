package com.doan.cv.util.mapper;

import com.doan.cv.dto.request.UserRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;

public class UserMapper {
    public User toEntity(UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());

        return user;
    }

    public UserResponse toResponse(User user){
        UserResponse response = new UserResponse();

        response.setUserId(user.getUserId());
        response.setName(user.getName());

        return response;
    }
}
