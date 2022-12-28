package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final MapperUtil mapperUtil;

    private final ClientVendorRepository clientVendorRepository;

    public ClientVendorServiceImpl(MapperUtil mapperUtil, ClientVendorRepository clientVendorRepository) {
        this.mapperUtil = mapperUtil;
        this.clientVendorRepository = clientVendorRepository;
    }


    public ClientVendorDto findById(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("This client or vendor does not exist "));
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }
    @Override
    public List<ClientVendorDto> listAllClientVendors() {
        return clientVendorRepository.findAll().stream()
                .sorted(Comparator.comparing(ClientVendor::getClientVendorType))
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }
}

