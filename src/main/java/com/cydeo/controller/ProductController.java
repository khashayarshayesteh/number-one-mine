package com.cydeo.controller;

import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
     private final  ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}