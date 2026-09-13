package com.doan.cv.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
}
