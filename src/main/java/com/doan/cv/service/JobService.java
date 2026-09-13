package com.doan.cv.service;

import com.doan.cv.dto.request.JobCreateRequest;
import com.doan.cv.dto.response.JobResponse;
import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Job;
import com.doan.cv.entity.JobSkill;
import com.doan.cv.error.IdNotFoundException;
import com.doan.cv.mapper.JobMapper;
import com.doan.cv.repository.CompanyRepository;
import com.doan.cv.repository.JobRepository;
import com.doan.cv.repository.SkillRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SkillRepository skillRepository;

    public JobResponse createJob(JobCreateRequest jobCreateRequest){
        return this.jobMapper.toResponse(this.jobRepository.save(this.jobMapper.toEntity(jobCreateRequest)));
    }


    public JobResponse getJobById(Long id) {
        return this.jobMapper.toResponse(this.jobRepository.findById(id)
                                                           .orElseThrow(() -> new IdNotFoundException("Job not found with id: "+id)));
    }

    public ResultPagination<List<JobResponse>> getAllJobs(Pageable pageable) {
        Page<Job> jobPage =  this.jobRepository.findAll(pageable);

        List<JobResponse> jobResponseList = jobPage.getContent().stream().map(this.jobMapper::toResponse).toList();

        ResultPagination<List<JobResponse>> resultPagination = new ResultPagination<>();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        meta.setPage(pageable.getPageNumber()+1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalPages(jobPage.getTotalPages());
        meta.setTotalElements(jobPage.getTotalElements());

        resultPagination.setMeta(meta);
        resultPagination.setResult(jobResponseList);

        return resultPagination;
    }

    public JobResponse updateJob(Long id, @Valid JobCreateRequest request) {
        Job existingJob = this.jobRepository.findById(id)
                                            .orElseThrow(() -> new IdNotFoundException("Job not found with id: "+id));

        existingJob.setName(request.getName());
        existingJob.setLocation(request.getLocation());
        existingJob.setSalary(request.getSalary());
        existingJob.setQuantity(request.getQuantity());
        existingJob.setLevel(request.getLevel());
        existingJob.setDescription(request.getDescription());
        existingJob.setStartDate(request.getStartDate());
        existingJob.setEndDate(request.getEndDate());

        existingJob.setCompany(this.companyRepository.findById(request.getCompanyId())
                                                     .orElseThrow(() -> new IdNotFoundException("Company not existed with ID: " + request.getCompanyId())));

        List<JobSkill> jobSkills = this.skillRepository.findBySkillIdIn(request.getSkillIds()).stream()
                                                                                         .map(s -> JobSkill.builder()
                                                                                                                .job(existingJob)
                                                                                                                .skill(s)
                                                                                                                .build())
                                                                                         .collect(Collectors.toList());
        existingJob.getJobSkills().clear();
        existingJob.getJobSkills().addAll(jobSkills);

        return this.jobMapper.toResponse(this.jobRepository.save(existingJob));
    }

    public void deleteJob(Long id) {
        Job existtingJob = this.jobRepository.findById(id)
                               .orElseThrow(() -> new IdNotFoundException("Job not found with id: "+id));
        this.jobRepository.delete(existtingJob);
    }
}
