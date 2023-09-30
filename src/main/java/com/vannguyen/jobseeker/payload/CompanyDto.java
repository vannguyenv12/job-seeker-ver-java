package com.vannguyen.jobseeker.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyDto {
    @NotBlank(message = "Name is required")
    private String name;
}
