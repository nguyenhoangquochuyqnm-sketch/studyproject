package com.doan.cv.entity;

import com.doan.cv.constant.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String email;
    private String password;

    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private String createdBy;
    private String updateBy;
}