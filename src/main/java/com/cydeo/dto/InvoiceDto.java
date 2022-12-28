package com.cydeo.dto;

import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceDto {
    Long id;

    private String invoiceNo;

    private InvoiceStatus invoiceStatus;

    private InvoiceType invoiceType;

    private LocalDate date;

    private ClientVendorDto clientVendor;

    private  CompanyDto company;

    private BigDecimal price;
    private Integer tax;
    private BigDecimal total;
}
