package com.cydeo.service.impl;

import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    private final MapperUtil mapperUtil;
    private final ProductRepository productRepository;

    public ProductServiceImpl(MapperUtil mapperUtil, ProductRepository productRepository) {
        this.mapperUtil = mapperUtil;
        this.productRepository = productRepository;
    }


    @Override
    public ProductDto findById(Long id) {
       Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("This product does not exist "));
        return mapperUtil.convert(product, new ProductDto());
    }
}
