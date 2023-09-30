package com.vannguyen.jobseeker.service.impl;

import com.vannguyen.jobseeker.aspect.CurrentUser;
import com.vannguyen.jobseeker.entity.Company;
import com.vannguyen.jobseeker.entity.Job;
import com.vannguyen.jobseeker.entity.User;
import com.vannguyen.jobseeker.exception.NotFoundException;
import com.vannguyen.jobseeker.exception.UnauthorizeException;
import com.vannguyen.jobseeker.helpers.Pagination;
import com.vannguyen.jobseeker.payload.JobDto;
import com.vannguyen.jobseeker.payload.Paginate;
import com.vannguyen.jobseeker.repository.CompanyRepository;
import com.vannguyen.jobseeker.repository.JobRepository;
import com.vannguyen.jobseeker.repository.UserRepository;
import com.vannguyen.jobseeker.service.JobService;
import com.vannguyen.jobseeker.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final UserService userService;

    private final Pagination<JobDto> pagination;
    private final ModelMapper modelMapper;


    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public JobDto createJob(JobDto jobDto, @CurrentUser User user1) {

        Company company = this.companyRepository.findCompanyByName(jobDto.getCompanyName()).orElseThrow(() -> new NotFoundException("Company", "name", jobDto.getCompanyName()));

        Job job = this.modelMapper.map(jobDto, Job.class);

        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setRequiredSkill(jobDto.getRequiredSkill());
        job.setSalary(jobDto.getSalary());
        job.setEndedAt(jobDto.getEndedAt());
        job.setUser(userService.getLoggedUser());
        job.setCompany(company);

        Job savedJob = jobRepository.save(job);

        return this.modelMapper.map(savedJob, JobDto.class);
    }

    @Override
    public Paginate<JobDto> getAllJobs(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
         Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Job> jobPage = this.jobRepository.findAll(pageable);

        List<Job> jobList = jobPage.getContent();

        List<JobDto> jobDtoList = jobList.stream().map(job -> this.modelMapper.map(job, JobDto.class)).toList();

        return pagination.build(jobDtoList, jobPage);
    }

    @Override
    public JobDto getJobById(long id) {

        Job job = this.jobRepository.findById(id).orElseThrow(() -> new NotFoundException("Job", "id", "" + id));

        return this.modelMapper.map(job, JobDto.class);
    }

    @Override
    public JobDto updateJobById(long id, JobDto jobDto) {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new NotFoundException("Job", "id", "" + id));
        job.setTitle(jobDto.getTitle());
        job.setSalary(jobDto.getSalary());
        job.setEndedAt(jobDto.getEndedAt());
        job.setRequiredSkill(jobDto.getRequiredSkill());
        job.setJobTypes(jobDto.getJobTypes());
        job.setDescription(jobDto.getDescription());
        job.setAddress(jobDto.getAddress());

        Job updatedJob = this.jobRepository.save(job);

        return this.modelMapper.map(updatedJob, JobDto.class);
    }

    @Override
    public String deleteJobById(long id) {
        Job job = this.jobRepository.findById(id).orElseThrow(() -> new NotFoundException("Job", "id", "" + id));
        this.jobRepository.delete(job);

        return "Remove Job Successfully";
    }

    @Override
    public Paginate<JobDto> getMyJobs(int pageNo, int pageSize) {
        // SELECT * FROM jobs JOIN users ON users.id = jobs.userId
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Job> jobPage = this.jobRepository.getJobByUser(userService.getLoggedUser(), pageable);
        List<Job> jobList = jobPage.getContent();

        List<JobDto> jobDtoList = jobList.stream().map(job ->this.modelMapper.map(job, JobDto.class)).toList();
        return pagination.build(jobDtoList, jobPage);
    }

    @Override
    public Paginate<JobDto> getJobByCompanyName(String companyName, int pageNo, int pageSize) {
        if (!this.companyRepository.existsByName(companyName)) {
            throw new NotFoundException("Company", "name", companyName);
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Job> jobPage = this.jobRepository.findJobsByCompanyName(companyName, pageable);
        List<Job> jobList = jobPage.getContent();
        List<JobDto> jobDtoList = jobList.stream().map((job) -> this.modelMapper.map(job, JobDto.class)).toList();
        return pagination.build(jobDtoList, jobPage);
    }
}
