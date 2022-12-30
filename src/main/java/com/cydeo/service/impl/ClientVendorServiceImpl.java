package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final MapperUtil mapperUtil;


    private final ClientVendorRepository clientVendorRepository;
    private final CompanyService companyService;

    public ClientVendorServiceImpl(MapperUtil mapperUtil, ClientVendorRepository clientVendorRepository, CompanyService companyService) {
        this.mapperUtil = mapperUtil;
        this.clientVendorRepository = clientVendorRepository;
        this.companyService = companyService;
    }

    public ClientVendorDto findById(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("This client or vendor does not exist "));
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }
    @Override
    public List<ClientVendorDto> listAllClientVendors() {
        List<ClientVendor> clientVendorList = clientVendorRepository.findAll();
        return clientVendorList.stream()
                .filter(clientVendor -> clientVendor.getCompany().getTitle().equals(companyService.getCompanyDtoByLoggedInUser().getTitle()))
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .sorted(Comparator.comparing(ClientVendorDto::getClientVendorType).reversed()
                        .thenComparing(ClientVendorDto::getClientVendorName))
                .collect(Collectors.toList());
    }

}

