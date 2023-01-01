package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDto findById(Long id);

    List<ClientVendorDto> listCompanyVendors();


    List<ClientVendorDto> listAllClientVendors();

    void save(ClientVendorDto clientVendorDto);

    ClientVendorDto updateClientVendor(ClientVendorDto clientVendorDto);

    Object findClientVendorAddress(Long  id);
}
