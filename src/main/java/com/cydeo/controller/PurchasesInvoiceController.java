package com.cydeo.controller;


import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {
    private final InvoiceService invoiceService;


    public PurchasesInvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;

    }

    @GetMapping("/list")
    String listPurchaseInvoices(Model model){

        model.addAttribute("invoices",invoiceService.listPurchaseInvoices());

        return "/invoice/purchase-invoice-list";

    }

}
