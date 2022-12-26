package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final MapperUtil mapperUtil;

    private final ClientVendorRepository clientVendorRepository;

    public ClientVendorServiceImpl(MapperUtil mapperUtil, ClientVendorRepository clientVendorRepository) {
        this.mapperUtil = mapperUtil;
        this.clientVendorRepository = clientVendorRepository;
    }


    @Override
    public ClientVendorDto findById(Long id) {

        Optional<ClientVendor> clientVendor= clientVendorRepository.findById(id);
        return clientVendor.map(mapperUtil::convertToDto).orElse(null);
    }
}
