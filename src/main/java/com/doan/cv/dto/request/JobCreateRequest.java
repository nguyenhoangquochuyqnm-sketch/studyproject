package com.doan.cv.dto.request;

import com.doan.cv.constant.JobLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobCreateRequest {

    @NotBlank(message = "name is required")
    private String name;
    private String location;
    private Long salary;
    private Integer quantity;

    private JobLevel level;
    private String description;

    private Instant startDate;
    private Instant endDate;

    private Long companyId;
    private List<Long> skillIds;
}
