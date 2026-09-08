package com.doan.cv.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.Instant;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name can not be blank")
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String address;
    private String logo;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    void handleCreatedAt(){
        this.createdAt = Instant.now();
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        String creator = principal.getName();
        this.setCreatedBy(creator);
    }

    @PreUpdate
    void handleUpdateAt(){
        this.updatedAt = Instant.now();
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        String editor = principal.getName();
        this.setUpdatedBy(editor);
    }
}
