package com.cydeo.controller;

import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {
private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/list")
    public String listAllCategory(Model model){
        model.addAttribute("categories",categoryService.listAllCategories());
        return "category/category-list";
    }
}
