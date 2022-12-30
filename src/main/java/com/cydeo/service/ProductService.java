package com.cydeo.service;


import com.cydeo.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto getProductById(Long id);
    List<ProductDto> listAllProducts();
    void save(ProductDto dto);
    void update(ProductDto dto);
    void deleteById(Long id);
}
