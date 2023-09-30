package com.vannguyen.jobseeker.repository;

import com.vannguyen.jobseeker.entity.Job;
import com.vannguyen.jobseeker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> getJobByUser(User user, Pageable pageable);

    Page<Job> findJobsByCompanyName(String companyName, Pageable pageable);

}
