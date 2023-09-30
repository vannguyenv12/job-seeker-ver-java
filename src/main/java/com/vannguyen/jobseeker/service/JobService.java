package com.vannguyen.jobseeker.service;

import com.vannguyen.jobseeker.entity.User;
import com.vannguyen.jobseeker.payload.JobDto;
import com.vannguyen.jobseeker.payload.Paginate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JobService {
//    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN')")
    JobDto createJob(JobDto jobDto, User user);


    Paginate<JobDto> getAllJobs(int pageNo, int pageSize, String sortBy, String sortDir);

    JobDto getJobById(long id);

    JobDto updateJobById(long id, JobDto jobDto);

    String deleteJobById(long id);

    Paginate<JobDto> getMyJobs(int pageNo, int pageSize);

    Paginate<JobDto> getJobByCompanyName(String companyName, int pageNo, int pageSize);
}
