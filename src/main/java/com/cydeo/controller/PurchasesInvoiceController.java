package com.cydeo.controller;
/**
 * @PostMapping("/create") save purchase invoice method will be redirected to purchaseUpdate
 */

import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {
    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;


    public PurchasesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;

        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
    }

    @GetMapping("/list")
    String listPurchaseInvoices(Model model) {

        model.addAttribute("invoices", invoiceService.listPurchaseInvoices());

        return "/invoice/purchase-invoice-list";

    }

    @GetMapping("/update/{invoice}")
    public String editPurchasesInvoice(@PathVariable("invoice") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.listCompanyVendors());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("products", productService.listAllProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceId(id));


        return "/invoice/purchase-invoice-update";

    }

    @PostMapping("/update/{id}")
    public String saveUpdateIntoPurchaseList(@ModelAttribute InvoiceDto invoiceDto, @PathVariable("id") Long id) {
        invoiceService.update(invoiceDto);
        return "redirect:/purchaseInvoices/update/"+id;


    }

    @PostMapping("/addInvoiceProduct/{invoiceId}")
    public String addProductIntoProductList(@Valid @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto, BindingResult bindingResult, @PathVariable("invoiceId") Long id, Model model) {
        if (bindingResult.hasErrors()){

            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("vendors", clientVendorService.listCompanyVendors());
            model.addAttribute("products", productService.listAllProducts());
            model.addAttribute("invoiceProducts", invoiceProductService.findByInvoiceId(id));

            return "/invoice/purchase-invoice-update";
        }

        invoiceProductService.add(invoiceProductDto,id);
        return "redirect:/purchaseInvoices/update/"+id;

    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProuductId}")
    public String deleteProductFromProductList(@PathVariable("invoiceId") Long id, @PathVariable("invoiceProuductId") Long id2){
        invoiceProductService.delete(id2);
        return "redirect:/purchaseInvoices/update/"+id;
    }



    @GetMapping("/create")
    public String createPurchaseInvoice(Model model) {

        model.addAttribute("newPurchaseInvoice", invoiceService.generateNewInvoiceDto());
        model.addAttribute("vendors", invoiceService.listCompanyVendors());


        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String savePurchaseInvoice(@Valid @ModelAttribute("newPurchaseInvoice") InvoiceDto invoiceDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("newPurchaseInvoice", invoiceService.generateNewInvoiceDto());
            model.addAttribute("vendors", invoiceService.listCompanyVendors());

            return "/invoice/purchase-invoice-create";

        }

        invoiceService.savePurchaseInvoice(invoiceDto);


        return "redirect:/purchaseInvoices/create";
    }


}
