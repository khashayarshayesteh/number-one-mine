package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDto;

import java.util.List;

public interface InvoiceProductService {
    InvoiceProductDto findById(Long id);
    List<InvoiceProductDto> findByInvoiceId(Long id);
    void save(InvoiceProductDto invoiceProductDto);

    void add(InvoiceProductDto invoiceProductDto, Long invoiceId);

  //  void delete(Long invoiceId, Long invoiceProductId);
    void delete(Long id);


}
