package com.cydeo.service;

import com.cydeo.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);

    List<UserDto> listAllUsers();

    List<UserDto> listAllByLoggedInUser();

    UserDto findById(Long id);

    void update(UserDto userDto);

    void deleteById(Long id);

    void save(UserDto user);
}
