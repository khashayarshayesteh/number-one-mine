package com.cydeo;

import com.cydeo.dto.AddressDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.enums.ClientVendorType;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NumberOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberOneApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


