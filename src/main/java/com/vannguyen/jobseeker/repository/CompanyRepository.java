package com.vannguyen.jobseeker.repository;

import com.vannguyen.jobseeker.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByName(String name);

    boolean existsByName(String name);
}
