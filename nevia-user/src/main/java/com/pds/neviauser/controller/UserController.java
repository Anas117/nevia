package com.pds.neviauser.controller;

import com.pds.neviauser.dto.UserCreateDto;
import com.pds.neviauser.dto.UserDto;
import com.pds.neviauser.entities.UserEntity;
import com.pds.neviauser.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getSpecificUser/{email}")
    public UserEntity getUser(@PathVariable String email) {
        log.info("Get User with email : " + email);
        return userService.getUser(email);
    }

    @PostMapping("")
    public UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
        log.info("Create one user");
        return userService.createUser(userCreateDto);
    }

    @GetMapping("/checkUser/{email}")
    public Boolean checkUser(@PathVariable String email) {
        log.info("Check user with this email " + email);
        return userService.checkUser(email);
    }

    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        log.info("Request for get all users");
        return userService.getAllUsers();
    }

}
