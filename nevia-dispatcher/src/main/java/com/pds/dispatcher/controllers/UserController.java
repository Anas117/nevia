package com.pds.dispatcher.controllers;

import com.pds.dispatcher.dto.UserCreateDto;
import com.pds.dispatcher.dto.UserDto;
import com.pds.dispatcher.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<UserDto> getUser(@NonNull @PathVariable("email") String email){
        return Optional.ofNullable(userService.getUser(email))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/checkUser/{email}")
    public ResponseEntity<Boolean> checkUser(@NonNull @PathVariable("email") String email){
        return Optional.ofNullable(userService.checkUser(email))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
