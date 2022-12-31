package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {
    InvoiceDto findById(Long id);

    List<InvoiceDto> listPurchaseInvoices();

    void update(InvoiceDto invoiceDto);

    String findLastRecordedInvoice();

    String invoiceNoGenerator();

    InvoiceDto generateNewInvoiceDto();

    List<ClientVendorDto> listCompanyVendors();

    void savePurchaseInvoice(InvoiceDto invoiceDto);
    List<InvoiceDto> listSalesInvoices();
}
