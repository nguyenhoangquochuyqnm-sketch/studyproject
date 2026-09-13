package com.doan.cv.dto.response;

import com.doan.cv.constant.JobLevel;
import com.doan.cv.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
    private Long jobId;
    private String name;
    private String location;
    private Long salary;
    private Integer quantity;
    private JobLevel level;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    private List<String> skills;
    private CompanyResponse company;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanyResponse{
        private Long id;
        private String name;
    }
}
