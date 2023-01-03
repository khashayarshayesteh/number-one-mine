package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.Address;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.SecurityService;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final MapperUtil mapperUtil;
    private final ClientVendorRepository clientVendorRepository;
    private final SecurityService securityService;
    private final CompanyService companyService;

    public ClientVendorServiceImpl(MapperUtil mapperUtil, ClientVendorRepository clientVendorRepository, SecurityService securityService, CompanyService companyService) {
        this.mapperUtil = mapperUtil;
        this.clientVendorRepository = clientVendorRepository;
        this.securityService = securityService;
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

    @Override
    public List<ClientVendorDto> listCompanyVendors() {

        List<ClientVendor> vendorList = clientVendorRepository.findAll()
                .stream()
                .filter(clientOrVendor -> clientOrVendor.getClientVendorType().equals(ClientVendorType.VENDOR))
                .filter(companySelect -> companySelect.getCompany().getId() == securityService.getLoggedInUser().getCompany().getId())
                .collect(Collectors.toList());

        return vendorList.stream().map(vendor -> mapperUtil.convert(vendor, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {
        clientVendorDto.setCompany(companyService.getCompanyDtoByLoggedInUser());
        clientVendorRepository.save(mapperUtil.convert(clientVendorDto, new ClientVendor()));
    }

    @Override
    public List<ClientVendorDto> listCompanyClients() {
        List<ClientVendor> clientList = clientVendorRepository.findAll()
                .stream()
                .filter(clientOrVendor -> clientOrVendor.getClientVendorType().equals(ClientVendorType.CLIENT))
                .filter(companySelect -> companySelect.getCompany().getId() == securityService.getLoggedInUser().getCompany().getId())
                .collect(Collectors.toList());

        return clientList.stream().map(vendor -> mapperUtil.convert(vendor, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findClientVendorById(Long id) {
        return mapperUtil.convert(clientVendorRepository.findById(id).get(), new ClientVendorDto());
    }

    @Override
    public List listAllClientVendorTypes() {
        List<ClientVendorType> types = new ArrayList<>();
        return listAllClientVendors().stream().map(ClientVendorDto::getClientVendorType).collect(Collectors.toList());
    }

    @Override
    public Address findClientVendorAddress(Long id) {
        return clientVendorRepository.findById(id).get().getAddress();
    }

    @Override
    public ClientVendorDto updateClientVendor(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = clientVendorRepository.findById(clientVendorDto.getId()).get();
        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());



//        updatedClientVendor.setId(clientVendor.getId());
//        clientVendorRepository.save(updatedClientVendor);


        updatedClientVendor.setClientVendorType(clientVendorDto.getClientVendorType());
        updatedClientVendor.setPhone(clientVendorDto.getPhone());
        updatedClientVendor.setAddress(updatedClientVendor.getAddress());
        updatedClientVendor.setCompany(mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()));
        updatedClientVendor.setClientVendorType(clientVendorDto.getClientVendorType());
        updatedClientVendor.setWebsite(clientVendor.getWebsite());

        clientVendorRepository.save(updatedClientVendor);
        return mapperUtil.convert(updatedClientVendor, new ClientVendorDto());
    }
}





