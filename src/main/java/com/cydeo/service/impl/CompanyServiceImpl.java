package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService) {
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
    @Override
    public List<CompanyDto> listAllCompanies() {
        return companyRepository.findAll().stream()
                .filter(company -> company.getId() != 1)
                .map(company -> mapperUtil.convert(company, new CompanyDto()))
                .sorted(Comparator.comparing((CompanyDto companyDto) -> companyDto.getCompanyStatus().equals(CompanyStatus.ACTIVE))
                        .thenComparing(CompanyDto::getTitle))
                .collect(Collectors.toList());
    }

}
