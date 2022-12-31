package com.cydeo.repository;

import com.cydeo.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "select * from invoices where invoice_type='PURCHASE' order by invoice_no desc limit 1", nativeQuery = true)
    Invoice retrieveLargestPurchaseInvoiceNo();


}
