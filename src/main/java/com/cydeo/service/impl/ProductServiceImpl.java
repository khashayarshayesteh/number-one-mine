package com.cydeo.service.impl;


import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.ProductService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final MapperUtil mapperUtil;
    private final ProductRepository productRepository;
    private final CompanyService companyService;
    private final SecurityService securityService;
    private final CategoryRepository categoryRepository;



    public ProductServiceImpl(MapperUtil mapperUtil, ProductRepository productRepository, CategoryRepository categoryRepository, CompanyService companyService, SecurityService securityService) {
        this.mapperUtil = mapperUtil;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.companyService = companyService;
        this.securityService = securityService;
    }


    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findProductById(id);
        return mapperUtil.convert(product, new ProductDto());
    }


    @Override
    public List<ProductDto> listAllProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().getCompany().getTitle().equals(companyService.getCompanyDtoByLoggedInUser().getTitle()))
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                .thenComparing(Product::getName))
                        .map(product -> mapperUtil.convert(product, new ProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        productDto.getCategory().setCompany(companyService.getCompanyDtoByLoggedInUser());
        Product product = mapperUtil.convert(productDto, new Product());
        Product product1= productRepository.save(product);
        return mapperUtil.convert(product1, new ProductDto());


    }

    @Override
    public void update(ProductDto productDto) {

        Product product = productRepository.findProductById(productDto.getId());
        ProductDto convertedProduct = mapperUtil.convert(product, new ProductDto());
        convertedProduct.setId(product.getId());
        convertedProduct.setCategory(productDto.getCategory());
        convertedProduct.setName(productDto.getName());
        convertedProduct.setProductUnit(productDto.getProductUnit());
        convertedProduct.setLowLimitAlert(productDto.getLowLimitAlert());
        productRepository.save(mapperUtil.convert(convertedProduct, new Product()));
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean isNameExist(String name) {
        return productRepository.existsByNameAndAndCategory_Company_Title(name, companyService.getCompanyDtoByLoggedInUser().getTitle());
    }

    @Override
    public boolean checkAnyProductExist(Long id) {
        List <Product> products = productRepository.findAllByCategoryId(id);
        return products.size() > 0;
    }
    @Override
    public boolean isInStock(Long id) {
        return  productRepository.getQuantityInStock(id) > 0;
    }
}

