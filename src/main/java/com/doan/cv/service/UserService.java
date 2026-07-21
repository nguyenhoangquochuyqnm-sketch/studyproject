package com.doan.cv.service;

import com.doan.cv.dto.request.UserCreateRequest;
import com.doan.cv.dto.request.UserUpdateRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;
import com.doan.cv.error.DuplicateValueException;
import com.doan.cv.error.InvalidValueException;
import com.doan.cv.repository.UserRepository;
import com.doan.cv.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public List<UserResponse> getALlUsers(){
        return userRepository.findAll()
                             .stream()
                             .map(userMapper::toResponse)
                             .toList();
    }


    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new InvalidValueException("User not found with ID: "+id));

        return userMapper.toResponse(user);
    }


    public UserResponse createUser(UserCreateRequest userCreateRequest){

        if(userRepository.existsByEmail(userCreateRequest.getEmail()))
            throw new DuplicateValueException("This email already exists");

        User user = userMapper.createToEntity(userCreateRequest);

        return userMapper.toResponse(userRepository.save(user));
    }


    public UserResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User exsistingUser = userRepository
                             .findById(userUpdateRequest.getId())
                             .orElseThrow(() -> new InvalidValueException("User not found with ID: "+userUpdateRequest.getId()));

        this.userMapper.updateToEntity(userUpdateRequest, exsistingUser);

        return userMapper.toResponse(userRepository.save(exsistingUser));
    }


    public void deleteUser(Long id) {
        if(!userRepository.existsById(id))
            throw new InvalidValueException("USER NOT FOUND WITH ID: "+id);

        userRepository.deleteById(id);
    }
}
