package com.cydeo.service;

import com.cydeo.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto getCompanyDtoByLoggedInUser();

    CompanyDto findById(Long id);

    List<CompanyDto> listAllCompanies();





}
