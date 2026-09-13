package com.doan.cv.repository;

import com.doan.cv.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findBySkillIdIn(List<Long> skillIds);
    boolean existsByName(String name);
}
