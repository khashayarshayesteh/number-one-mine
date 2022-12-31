package com.cydeo.service.impl;


import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
/** ClientVendorRepository will be replaced with clientVendorService when clientVendorRepository.findAll() is provided
 *
 */
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;
    private final ClientVendorService clientVendorService;
    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceProductService invoiceProductService, CompanyService companyService, ClientVendorService clientVendorService, ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;
        this.clientVendorService = clientVendorService;
        this.clientVendorRepository = clientVendorRepository;
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

    @Override
    public String findLastRecordedInvoice() {
        InvoiceDto invoiceDto = mapperUtil.convert(invoiceRepository.retrieveLargestPurchaseInvoiceNo(), new InvoiceDto());
        return invoiceDto.getInvoiceNo();

    }

    public String invoiceNoGenerator() {
        int number = Integer.parseInt(findLastRecordedInvoice().substring(2));
        number++;
        String formatted = String.format("%03d", number);
        return "P-" + formatted;


    }


    public List<ClientVendorDto> listCompanyVendors() {

        List<ClientVendor> vendorList = clientVendorRepository.findAll()
                .stream()
                .filter(clientOrVendor -> clientOrVendor.getClientVendorType().equals(ClientVendorType.VENDOR))
                .filter(companySelect -> companySelect.getCompany().getId().equals(companyService.getCompanyDtoByLoggedInUser().getId()))
                .collect(Collectors.toList());

        return vendorList.stream().map(vendor -> mapperUtil.convert(vendor, new ClientVendorDto())).collect(Collectors.toList());

    }

    @Override
    public void savePurchaseInvoice(InvoiceDto invoiceDto) {
        invoiceDto.setCompany(companyService.getCompanyDtoByLoggedInUser());
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceDto.setInvoiceType(InvoiceType.PURCHASE);
        invoiceRepository.save(mapperUtil.convert(invoiceDto, new Invoice()));
    }

    @Override
    public InvoiceDto generateNewInvoiceDto() {
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.setInvoiceNo(invoiceNoGenerator());
        invoiceDto.setDate(LocalDate.now());
        invoiceDto.setInvoiceType(InvoiceType.PURCHASE);

        return invoiceDto;
    }

    @Override
    public List<InvoiceDto> listSalesInvoices() {

        List<InvoiceDto> companySalesInvoiceList = listCompanyInvoices().stream()
                .filter(invoice -> invoice.getInvoiceType().equals(InvoiceType.SALES))
                .collect(Collectors.toList());

        List<InvoiceDto> salesInvoiceList = companySalesInvoiceList.stream()
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
                )
                .sorted(Comparator.comparing(invoiceDto -> invoiceNoConverter(invoiceDto.getInvoiceNo())))
                .collect(Collectors.toList());


        return salesInvoiceList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toList(),
                        l -> {
                            Collections.reverse(l);
                            return l;
                        }
                )
        );
    }

    public static int invoiceNoConverter(String invoiceNo) {
        return Integer.parseInt(invoiceNo.substring(2));
    }



    @Override
    public void update(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceRepository.findById(invoiceDto.getId()).get();
        ClientVendor clientVendor = mapperUtil.convert(invoiceDto.getClientVendor(), new ClientVendor());
        invoice.setClientVendor(clientVendor);
        invoiceRepository.save(invoice);
    }


}
