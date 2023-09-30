package com.vannguyen.jobseeker.service.impl;

import com.vannguyen.jobseeker.entity.Company;
import com.vannguyen.jobseeker.entity.Job;
import com.vannguyen.jobseeker.exception.NotFoundException;
import com.vannguyen.jobseeker.helpers.Pagination;
import com.vannguyen.jobseeker.payload.CompanyDto;
import com.vannguyen.jobseeker.payload.JobDto;
import com.vannguyen.jobseeker.payload.Paginate;
import com.vannguyen.jobseeker.repository.CompanyRepository;
import com.vannguyen.jobseeker.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final Pagination<CompanyDto> pagination;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = this.modelMapper.map(companyDto, Company.class);

        Company savedCompany = this.companyRepository.save(company);

        return this.modelMapper.map(savedCompany, CompanyDto.class);

    }

    @Override
    public Paginate<CompanyDto> findAllCompanies(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Company> companyPage = this.companyRepository.findAll(pageable);
        List<Company> companyList = companyPage.getContent();
        List<CompanyDto> companyDtoList = companyList.stream().map(company -> this.modelMapper.map(company, CompanyDto.class)).toList();

        return pagination.build(companyDtoList, companyPage);


    }

    @Override
    public CompanyDto getCompanyById(long id) {
        Company company = this.companyRepository.findById(id).orElseThrow(() -> new NotFoundException("Company", "id", "" + id));
        return this.modelMapper.map(company, CompanyDto.class);
    }

    @Override
    public CompanyDto updateCompany(long id, CompanyDto companyDto) {
        Company company = this.companyRepository.findById(id).orElseThrow(() -> new NotFoundException("Company", "id", "" + id));
        company.setName(companyDto.getName());
        Company savedCompany = this.companyRepository.save(company);
        return this.modelMapper.map(savedCompany, CompanyDto.class);
    }

    @Override
    public String deleteCompany(long id) {
        Company company = this.companyRepository.findById(id).orElseThrow(() -> new NotFoundException("Company", "id", "" + id));

        this.companyRepository.delete(company);
        return "Delete Company Successfully!";
    }
}
