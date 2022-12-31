package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy SecurityService securityService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public List<UserDto> listAllUsers() {
        return userRepository.findAll().stream().map(user -> mapperUtil.convert(user, new UserDto()))
                .sorted(Comparator.comparing((UserDto user) -> user.getCompany().getTitle())
                        .thenComparing(userDto -> userDto.getRole().getDescription()))
                .collect(Collectors.toList());
    }


    @Override
    public List<UserDto> listAllByLoggedInUser() {
        if (securityService.getLoggedInUser().getRole().getDescription().equals("Admin")) {
            return listAllUsers()
                    .stream().filter(userDto -> userDto.getCompany()
                            .getId().equals(securityService.getLoggedInUser()
                                    .getCompany().getId())).collect(Collectors.toList());
        } else if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {
            return listAllUsers().stream().filter(userDto -> userDto.getRole().getDescription().equals("Admin")).collect(Collectors.toList());
        } else {
            throw new NoSuchElementException("No users is available");
        }
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public void update(UserDto userDto) {
        User user1 = userRepository.findById(userDto.getId()).get();
        User convertedUser = mapperUtil.convert(userDto, new User());
        convertedUser.setId(user1.getId());
        userRepository.save(convertedUser);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).get();
        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + "-" + user.getId());
        userRepository.save(user);
    }

    @Override
    public void save(UserDto user) {
        userRepository.save(mapperUtil.convert(user, new User()));
    }
}
