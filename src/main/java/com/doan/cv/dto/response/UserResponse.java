package com.doan.cv.dto.response;

import com.doan.cv.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String name;
    private String email;
    private int age;
    private Gender gender;
    private String address;
    private String companyName;
    private UserRole userRole;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRole{
        private Long roleId;
        private String roleName;
    }
}