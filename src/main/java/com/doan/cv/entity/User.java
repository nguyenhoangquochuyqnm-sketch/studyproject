package com.doan.cv.entity;

import com.doan.cv.constant.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "users")
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

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @PrePersist
    void handleCreatedAt(){
        this.setCreatedBy(getCurrentUsernameOrDefault());
    }
    @PreUpdate
    void handleUpdatedAt(){
        this.setUpdateBy(getCurrentUsernameOrDefault());
    }

    private String getCurrentUsernameOrDefault() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : "system";
    }
}