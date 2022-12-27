package com.cydeo.service;

import com.cydeo.dto.CompanyDto;

public interface CompanyService {

    CompanyDto getCompanyDtoByLoggedInUser();

    CompanyDto findById(Long id);
}
