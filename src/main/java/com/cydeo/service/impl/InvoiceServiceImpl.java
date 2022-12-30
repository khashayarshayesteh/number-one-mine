package com.cydeo.service.impl;


import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;
    private final MapperUtil mapperUtil;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceProductService invoiceProductService, CompanyService companyService, MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public InvoiceDto findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        return mapperUtil.convert(invoice, new InvoiceDto());
    }

    public List<InvoiceDto> listAllInvoices() {
        return invoiceRepository.findAll().stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDto())).collect(Collectors.toList());

    }

    public List<InvoiceDto> listCompanyInvoices() {
        Long companyId = companyService.getCompanyDtoByLoggedInUser().getId();
        return listAllInvoices().stream()
                .filter(invoiceDto -> invoiceDto.getCompany().getId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> listPurchaseInvoices() {

        List<InvoiceDto> purchaseList = listCompanyInvoices().stream()
                .filter(invoice -> invoice.getInvoiceType().equals(InvoiceType.PURCHASE))
                .collect(Collectors.toList());

        List<InvoiceDto> invoicePurchaseList = purchaseList.stream()
                .map(invoiceDto ->

                        {
                            Long invoiceId = invoiceDto.getId();
                            List<InvoiceProductDto> invoiceProductList = invoiceProductService.findByInvoiceId(invoiceId);

                            BigDecimal totalPrice = invoiceProductList.stream().map(invoiceProductDto -> invoiceProductDto.getPrice()
                                    .multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
                            BigDecimal totalTax = invoiceProductList.stream().map(invoiceProductDto -> invoiceProductDto.getPrice()
                                    .multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity())).multiply(BigDecimal.valueOf(invoiceProductDto.getTax()))
                                            .divide(BigDecimal.valueOf(100)))

                                    .reduce(BigDecimal.ZERO, BigDecimal::add);


                            invoiceDto.setPrice(totalPrice);
                            invoiceDto.setTax(totalTax.intValue());
                            invoiceDto.setTotal(totalPrice.add(totalTax));
                            return invoiceDto;
                        }
                ).collect(Collectors.toList());


        return invoicePurchaseList;
    }
}
