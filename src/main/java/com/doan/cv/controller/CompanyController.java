package com.doan.cv.controller;

import com.doan.cv.annotation.APImessage;
import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Company;
import com.doan.cv.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping
    @APImessage("fetch all companies")
    ResponseEntity<ResultPagination<List<Company>>> getAllCompanies(@RequestParam(name = "current", defaultValue = "1") int currentPage,
                                                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        if(currentPage < 1)
            currentPage = 1;
        if(pageSize < 1 || pageSize > 100)
            pageSize = 10;
        Pageable pageable = PageRequest.of(currentPage-1, pageSize);

        return ResponseEntity.ok().body(this.companyService.getAllCompanies(pageable));
    }

    @GetMapping("/{id}")
    @APImessage("fetch company by ID")
    ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.companyService.getCompanyById(id));
    }

    @PostMapping
    @APImessage("company created")
    ResponseEntity<Company> createCompany(@RequestBody @Valid Company company){
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(this.companyService.createCompany(company));
    }

    @PutMapping
    @APImessage("update company")
    ResponseEntity<Company> updateCompany(@RequestBody @Valid Company company){
        return ResponseEntity.ok(this.companyService.updateCompany(company));
    }

    @DeleteMapping("/{id}")
    @APImessage("delete company")
    ResponseEntity<Void> deleteCompany(@PathVariable Long id){
        this.companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
