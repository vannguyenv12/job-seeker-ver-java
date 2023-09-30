package com.vannguyen.jobseeker.controller;

import com.vannguyen.jobseeker.payload.CompanyDto;
import com.vannguyen.jobseeker.payload.Paginate;
import com.vannguyen.jobseeker.service.CompanyService;
import com.vannguyen.jobseeker.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final EmailServiceImpl emailService;


    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {

        return new ResponseEntity<>(this.companyService.createCompany(companyDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Paginate<CompanyDto>> getAllCompanies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {

        return new ResponseEntity<>(this.companyService.findAllCompanies(pageNo,pageSize,sortBy, sortDir), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(this.companyService.getCompanyById(id), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable(name = "id") long id, @RequestBody CompanyDto companyDto) {
        return new ResponseEntity<>(this.companyService.updateCompany(id, companyDto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(this.companyService.deleteCompany(id), HttpStatus.CREATED);
    }
}
