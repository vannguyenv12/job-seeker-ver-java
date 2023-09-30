package com.vannguyen.jobseeker.service;


import com.vannguyen.jobseeker.payload.CompanyDto;
import com.vannguyen.jobseeker.payload.Paginate;

import java.util.List;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto);

    Paginate<CompanyDto> findAllCompanies(int pageNo, int pageSize, String sortBy, String sortDir);

    CompanyDto getCompanyById(long id);

    CompanyDto updateCompany(long id, CompanyDto companyDto);

    String deleteCompany(long id);
}
