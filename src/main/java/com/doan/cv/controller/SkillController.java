package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Skill;
import com.doan.cv.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping
    @APImessage("fetch all skills")
    public ResponseEntity<ResultPagination<List<Skill>>> getAllSkills(@RequestParam(name = "current", defaultValue = "1") int currentPage,
                                                                      @RequestParam(name = "size", defaultValue = "10") int pageSize){
        if(currentPage < 1)
            currentPage = 1;
        if (pageSize < 1 || pageSize > 100)
            pageSize = 10;
        Pageable pageable = PageRequest.of(currentPage-1, pageSize);

        return ResponseEntity.ok(this.skillService.getAllSkills(pageable));
    }
    @PostMapping
    @APImessage("created a skill")
    public ResponseEntity<Skill> createSkill(@RequestBody @Valid Skill skill){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.createSkill(skill));
    }
}
