package com.doan.cv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @NotBlank(message = "skill name is required!")
    private String name;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<JobSkill> jobSkills;

    @PrePersist
    void handleCreate(){
        this.setCreatedAt(Instant.now());
        this.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreUpdate
    void handleUpdate(){
        this.setUpdatedAt(Instant.now());
        this.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
