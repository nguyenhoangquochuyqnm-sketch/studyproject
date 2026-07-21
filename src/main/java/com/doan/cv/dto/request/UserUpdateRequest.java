package com.doan.cv.dto.request;

import com.doan.cv.constant.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequest {

    @NotNull(message = "User ID cannot be null")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull
    private Gender gender;

    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    @NotBlank(message = "Address cannot be blank")
    private String address;
}