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
    private final CategoryRepository categoryRepository;
    private final CompanyService companyService;
    private final SecurityService securityService;

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
    public void save(ProductDto dto) {

    }

    @Override
    public void update(ProductDto dto) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
