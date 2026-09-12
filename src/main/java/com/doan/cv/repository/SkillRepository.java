package com.doan.cv.repository;

import com.doan.cv.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);
}
