package com.cydeo.service;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.ClientVendorDto;

import java.util.List;

public interface CategoryService {
   CategoryDto findById(Long id);

   List<CategoryDto> listAllCategories();


}

