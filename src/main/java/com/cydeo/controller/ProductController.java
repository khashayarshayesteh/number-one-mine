package com.cydeo.controller;


import com.cydeo.dto.ProductDto;
import com.cydeo.enums.ProductUnit;
import com.cydeo.service.CategoryService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@Controller
@RequestMapping("/products")
public class ProductController {
     private final  ProductService productService;
     private final CategoryService categoryService;


    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;

    }

    @GetMapping("/list")
    public String listAllProducts(Model model){
        model.addAttribute("products", productService.listAllProducts());
        return "/product/product-list";
    }
    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        return "product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct( @Valid @ModelAttribute("newProduct")  ProductDto productDto,BindingResult bindingResult,Model model){
        boolean isNameExist = productService.isNameExist(productDto.getName());
        if (isNameExist) {
            bindingResult.rejectValue("name", " ", "This product name already exist");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.listAllCategories());
            model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
            return "product/product-create";
        }

        productService.save(productDto);

        return "redirect:/products/list";
    }


    @GetMapping("/update/{id}")
    public String getUpdateProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        model.addAttribute("categories", categoryService.listAllCategories());

        return "product/product-update";
    }


    @PostMapping("/update/{id}")
    public String updateProduct(@ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
            model.addAttribute("categories", categoryService.listAllCategories());
            return "product/product-update";
        }
        productService.update(product);
        return "redirect:/products/list";
    }


}
