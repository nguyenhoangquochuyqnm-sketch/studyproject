package com.doan.cv.service;


import com.doan.cv.dto.response.ResultPagination;
import com.doan.cv.entity.Company;
import com.doan.cv.entity.User;
import com.doan.cv.error.IdNotFoundException;
import com.doan.cv.error.InvalidValueException;
import com.doan.cv.repository.CompanyRepository;
import com.doan.cv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    public ResultPagination<List<Company>> getAllCompanies(Pageable pageable) {
        Page<Company> companyPage = this.companyRepository.findAll(pageable);

        List<Company> companyList = companyPage.getContent().stream().toList();

        ResultPagination<List<Company>> resultPagination = new ResultPagination<>();
        ResultPagination.Meta meta = new ResultPagination.Meta();

        meta.setPage(pageable.getPageNumber()+1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalPages(companyPage.getTotalPages());
        meta.setTotalElements(companyPage.getTotalElements());

        resultPagination.setResult(companyList);
        resultPagination.setMeta(meta);

        return resultPagination;
    }

    public Company getCompanyById(Long id) {
        return this.companyRepository.findById(id)
                   .orElseThrow(() -> new InvalidValueException("Company not existed with ID: "+id));

    }

    public Company createCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public Company updateCompany(Company company) {
        Company existingCompany = this.companyRepository.findById(company.getCompanyId())
                                                        .orElseThrow(() -> new IdNotFoundException("company not found with ID: "+company.getCompanyId()));

        existingCompany.setName(company.getName());
        existingCompany.setDescription(company.getDescription());
        existingCompany.setAddress(company.getAddress());
        existingCompany.setLogo(company.getLogo());

        return this.companyRepository.save(existingCompany);
    }


    public void deleteCompany(Long id) {
        Company existingCompany = this.companyRepository.findById(id)
                                                        .orElseThrow(() -> new IdNotFoundException("company not found with ID: "+id));

        List<User> users = this.userRepository.findByCompany(existingCompany);
        this.userRepository.deleteAll(users);

        this.companyRepository.deleteById(id);
    }
}
