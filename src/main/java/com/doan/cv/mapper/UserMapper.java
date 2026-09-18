package com.doan.cv.mapper;

import com.doan.cv.dto.request.UserCreateRequest;
import com.doan.cv.dto.request.UserUpdateRequest;
import com.doan.cv.dto.response.UserResponse;
import com.doan.cv.entity.User;
import com.doan.cv.error.IdNotFoundException;
import com.doan.cv.repository.CompanyRepository;
import com.doan.cv.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User createToEntity(UserCreateRequest userCreateRequest) {
        User user = new User();

        user.setCompany(userCreateRequest.getCompanyId() != null ? this.companyRepository.findById(userCreateRequest.getCompanyId())
                                                                                         .orElseThrow(() -> new IdNotFoundException("company not found: ID: "+userCreateRequest.getCompanyId()))
                                                                 : null);
        user.setRole(userCreateRequest.getRoleId() != null ? this.roleRepository.findById(userCreateRequest.getRoleId())
                                                                                .orElseThrow(() -> new IdNotFoundException("Role not found: ID: "+userCreateRequest.getRoleId()))
                                                           : null);
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
        response.setCompanyName(user.getCompany() != null ? user.getCompany().getName() : null);
        response.setUserRole(user.getRole()!=null ? new UserResponse.UserRole(user.getRole().getRoleId(), user.getRole().getName()) : null);
        return response;
    }

    public void updateToEntity(UserUpdateRequest source, User target){
        target.setCompany(source.getCompanyId() != null ? this.companyRepository.findById(source.getCompanyId())
                                                                                .orElseThrow(() -> new IdNotFoundException("company not found: ID: "+source.getCompanyId()))
                                                        : null);

        target.setRole(source.getRoleId() != null ? this.roleRepository.findById(source.getRoleId())
                                                                       .orElseThrow(() -> new IdNotFoundException("Role not found: ID: "+source.getRoleId()))
                                                  : null);
        target.setName(source.getName());
        target.setAge(source.getAge());
        target.setAddress(source.getAddress());
        target.setGender(source.getGender());
    }
}