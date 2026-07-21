package com.doan.cv.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequest {

    @Email(message = "Email must not be blank")
    private String email;

    @NotNull(message = "password must not be null")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

}
