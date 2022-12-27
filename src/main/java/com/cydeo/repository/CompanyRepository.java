package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
