package com.cydeo.controller;

import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {


    private final InvoiceService invoiceService;

    public SalesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;

    }


    @GetMapping("/list")
    String listSalesInvoices(Model model) {

        model.addAttribute("invoices", invoiceService.listSalesInvoices());

        return "/invoice/sales-invoice-list";

    }
}
