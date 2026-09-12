package com.doan.cv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_skill")
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
