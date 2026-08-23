package com.doan.cv.service;

import com.doan.cv.dto.request.UserCreateRequest;
import com.doan.cv.dto.request.UserUpdateRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;
import com.doan.cv.error.DuplicateValueException;
import com.doan.cv.error.InvalidValueException;
import com.doan.cv.repository.UserRepository;
import com.doan.cv.mapper.UserMapper;
import com.doan.cv.util.JWTUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
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

    public String updateUserRefreshToken(String email){
        User currentUser = this.userRepository
                               .findByEmail(email)
                               .orElseThrow(() -> new InvalidValueException("This Email: " + email + " does not exists"));

        String refreshToken = this.jwtUtil.generateRefreshToken(email, this.userMapper.toResponse(currentUser));

        currentUser.setRefreshToken(refreshToken);
        this.userRepository.save(currentUser);

        return refreshToken;
    }

    public UserResponse getCurrentUserLogin(){
        String email = jwtUtil.getCurrentUserLogin();

        User currentUser = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidValueException("This Email: " + email + " does not exists"));

        return this.userMapper.toResponse(currentUser);
    }

    public boolean validateUserRefreshToken(String email, String refreshToken) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("EMAIL NOT FOUND"));

        return user.getRefreshToken() != null && user.getRefreshToken().equals(refreshToken);
    }

    public void logoutUser() {
        String email = this.jwtUtil.getCurrentUserLogin();

        User currentUser = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new InvalidValueException("This Email: " + email + " does not exists"));

        currentUser.setRefreshToken(null);
    }
}
