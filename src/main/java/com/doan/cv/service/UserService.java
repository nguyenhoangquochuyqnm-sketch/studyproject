package com.doan.cv.service;

import com.doan.cv.dto.request.UserRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;
import com.doan.cv.repository.UserRepository;
import com.doan.cv.util.mapper.UserMapper;
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
                                  .orElseThrow(() -> new RuntimeException("User not found "+id));

        return userMapper.toResponse(user);
    }


    public UserResponse createUser(UserRequest userRequest){
        User user = userMapper.toEntity(userRequest);

        return userMapper.toResponse(userRepository.save(user));
    }


    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User exsistingUser = userRepository
                             .findById(id)
                             .orElseThrow(() -> new RuntimeException("User not found "+id));

        exsistingUser.setName(userRequest.getName());
        exsistingUser.setPassword(userRequest.getPassword());

        return userMapper.toResponse(userRepository.save(exsistingUser));
    }


    public void deleteUser(Long id) {
        if(!userRepository.existsById(id))
            throw new RuntimeException("USER NOT FOUND "+id);

        userRepository.deleteById(id);
    }
}
