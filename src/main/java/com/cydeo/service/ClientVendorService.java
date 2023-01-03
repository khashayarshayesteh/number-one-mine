package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Address;

import java.net.DatagramPacket;
import java.util.List;

public interface ClientVendorService {

    ClientVendorDto findById(Long id);

    List<ClientVendorDto> listCompanyVendors();

    List<ClientVendorDto> listAllClientVendors();

    void save(ClientVendorDto  clientVendorDto);
    List<ClientVendorDto> listCompanyClients();

    ClientVendorDto findClientVendorById(Long  id);

    List listAllClientVendorTypes();

    Address findClientVendorAddress(Long  id);

    ClientVendorDto updateClientVendor(ClientVendorDto  clientVendorDto);
}
