package com.vannguyen.jobseeker.payload;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Set;

@Data
public class SkillDto {
    private String name;
    private Set<String> nameList;
    private Set<Long> userId;
}
