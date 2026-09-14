package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.request.JobCreateRequest;
import com.doan.cv.dto.response.JobResponse;
import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    @APImessage("job created")
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobCreateRequest request) {
        JobResponse response = jobService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @APImessage("fetch job by ID")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @APImessage("fetch all jobs")
    public ResponseEntity<ResultPagination<List<JobResponse>>> getAllJobs(@RequestParam(name = "current", defaultValue = "1") int currentPage,
                                                                          @RequestParam(name = "size", defaultValue = "10") int pageSize){
        if(currentPage < 1)
            currentPage = 1;
        if (pageSize < 1 || pageSize > 100)
            pageSize = 10;

        Pageable pageable = PageRequest.of(currentPage-1, pageSize);

        return ResponseEntity.ok(jobService.getAllJobs(pageable));
    }

    @PutMapping("/{id}")
    @APImessage("update job")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @Valid @RequestBody JobCreateRequest request) {
        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    @DeleteMapping("/{id}")
    @APImessage("delete job")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}