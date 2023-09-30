package com.vannguyen.jobseeker.payload;

import com.vannguyen.jobseeker.entity.JobTypes;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class JobDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String requiredSkill;

    private String address;
    private double salary;

    @NotBlank(message = "JobTypes is required")
    private JobTypes jobTypes;

    private String companyName;

    private Date endedAt;
}
