package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class companyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public companyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public CompanyDto getCompanyDtoByLoggedInUser() {

        return securityService.getLoggedInUser().getCompany();
    }

    @Override
    public CompanyDto findById(Long id) {
        Company company= companyRepository.findById(id).orElseThrow(()->new NoSuchElementException("Company not found"));
        return mapperUtil.convert(company, new CompanyDto());
    }

}
