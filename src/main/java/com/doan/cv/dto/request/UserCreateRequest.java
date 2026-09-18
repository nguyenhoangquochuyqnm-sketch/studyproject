package com.doan.cv.dto.request;

import com.doan.cv.constant.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    @Min(value = 0, message = "Age must be a positive number")
    private Integer age;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private Long roleId;

    private Long companyId;
}