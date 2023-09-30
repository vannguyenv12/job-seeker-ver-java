package com.vannguyen.jobseeker.controller;

import com.vannguyen.jobseeker.aspect.CurrentUser;
import com.vannguyen.jobseeker.entity.User;
import com.vannguyen.jobseeker.payload.JobDto;
import com.vannguyen.jobseeker.payload.Paginate;
import com.vannguyen.jobseeker.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/jobs")
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobDto jobDto, @CurrentUser User user) {
        return new ResponseEntity<>(jobService.createJob(jobDto, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Paginate<JobDto>> findAllJobs(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(jobService.getAllJobs(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<Paginate<JobDto>> findMyJobs(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(jobService.getMyJobs(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/companies/{companyName}")
    public ResponseEntity<Paginate<JobDto>> findJobsByCompanyName(@PathVariable(name = "companyName") String companyName,
                                                              @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(jobService.getJobByCompanyName(companyName, pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> findJob(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJob(@PathVariable(name = "id") long id, @RequestBody JobDto jobDto) {
        return new ResponseEntity<>(jobService.updateJobById(id, jobDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(jobService.deleteJobById(id), HttpStatus.OK);
    }
}
