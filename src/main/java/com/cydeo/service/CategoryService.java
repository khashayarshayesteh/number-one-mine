package com.cydeo.service;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.ClientVendorDto;

public interface CategoryService {
   CategoryDto findById(Long id);
}

