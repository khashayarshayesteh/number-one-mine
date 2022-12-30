package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {
    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public InvoiceProductDto findById(Long id) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElseThrow();
        return mapperUtil.convert(invoiceProduct, new InvoiceProductDto());
    }



    @Override
    public List<InvoiceProductDto> findByInvoiceId(Long id) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoice_Id(id);
        return invoiceProductList.stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct,new InvoiceProductDto())).collect(Collectors.toList());

    }
}
