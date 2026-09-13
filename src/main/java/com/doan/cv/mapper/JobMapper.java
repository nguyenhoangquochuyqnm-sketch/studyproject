package com.doan.cv.mapper;

import com.doan.cv.dto.request.JobCreateRequest;
import com.doan.cv.dto.response.JobResponse;
import com.doan.cv.entity.Job;
import com.doan.cv.entity.JobSkill;
import com.doan.cv.error.IdNotFoundException;
import com.doan.cv.repository.CompanyRepository;
import com.doan.cv.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public JobResponse toResponse(Job job){
        JobResponse jobResponse = new JobResponse();

        jobResponse.setJobId(job.getJobId());
        jobResponse.setName(job.getName());
        jobResponse.setLocation(job.getLocation());
        jobResponse.setSalary(job.getSalary());
        jobResponse.setQuantity(job.getQuantity());
        jobResponse.setLevel(job.getLevel());
        jobResponse.setDescription(job.getDescription());
        jobResponse.setStartDate(job.getStartDate());
        jobResponse.setEndDate(job.getEndDate());
        jobResponse.setIsActive(job.getIsActive());
        jobResponse.setCreatedAt(job.getCreatedAt());
        jobResponse.setUpdatedAt(job.getUpdatedAt());
        jobResponse.setCreatedBy(job.getCreatedBy());
        jobResponse.setUpdatedBy(job.getUpdatedBy());

        JobResponse.CompanyResponse companyResponse = new JobResponse.CompanyResponse();
        companyResponse.setId(job.getCompany().getCompanyId());
        companyResponse.setName(job.getCompany().getName());

        jobResponse.setCompany(companyResponse);

        if(!job.getJobSkills().isEmpty()){
            List<Long> skillIds = job.getJobSkills().stream().map(js -> js.getSkill().getSkillId()).toList();
            jobResponse.setSkills(this.skillRepository.findBySkillIdIn(skillIds).stream().map(skill -> skill.getName()).toList());
        }
        return jobResponse;
    }

    public Job toEntity(JobCreateRequest jobCreateRequest){
        Job job = new Job();

        job.setName(jobCreateRequest.getName());
        job.setLocation(jobCreateRequest.getLocation());
        job.setSalary(jobCreateRequest.getSalary());
        job.setQuantity(jobCreateRequest.getQuantity());
        job.setLevel(jobCreateRequest.getLevel());
        job.setDescription(jobCreateRequest.getDescription());
        job.setStartDate(jobCreateRequest.getStartDate());
        job.setEndDate(jobCreateRequest.getEndDate());
        job.setCompany(this.companyRepository.findById(jobCreateRequest.getCompanyId())
                                                                       .orElseThrow((()-> new IdNotFoundException("Company not existed with ID: "+jobCreateRequest.getCompanyId()))));

        List<JobSkill> jobSkillList = this.skillRepository.findBySkillIdIn(jobCreateRequest.getSkillIds()).stream().map(s -> JobSkill.builder()
                                                                                                                               .job(job)
                                                                                                                               .skill(s)
                                                                                                                               .build())
                                                                                                              .collect(Collectors.toList());
        job.setJobSkills(jobSkillList);

        return job;
    }
}
