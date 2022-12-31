package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    @NotBlank(message = "Description is a required field.")
    @Size(min = 2, max = 200, message = "Description should have 2-100 characters long.")
    private String description;
    private CompanyDto company;
    private boolean hasProduct;


}
