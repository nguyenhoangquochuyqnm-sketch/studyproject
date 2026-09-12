package com.doan.cv.service;

import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Skill;
import com.doan.cv.error.DuplicateValueException;
import com.doan.cv.repository.SkillRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    SkillRepository skillRepository;

    public Skill createSkill( Skill skill) {
        if(this.skillRepository.existsByName(skill.getName()))
            throw new DuplicateValueException("skill name already existed");

        return this.skillRepository.save(skill);
    }

    public ResultPagination<List<Skill>> getAllSkills(Pageable pageable) {
        Page<Skill> skillPage = this.skillRepository.findAll(pageable);

        List<Skill> skills = skillPage.getContent().stream().toList();

        ResultPagination<List<Skill>> resultPagination = new ResultPagination<>();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        meta.setPage(pageable.getPageNumber()+1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalPages(skillPage.getTotalPages());
        meta.setTotalElements(skillPage.getTotalElements());

        resultPagination.setResult(skills);
        resultPagination.setMeta(meta);

        return  resultPagination;
    }
}
