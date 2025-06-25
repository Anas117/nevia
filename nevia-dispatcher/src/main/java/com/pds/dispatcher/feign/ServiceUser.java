package com.pds.dispatcher.feign;

import com.pds.dispatcher.dto.UserCreateDto;
import com.pds.dispatcher.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface ServiceUser {

    @RequestMapping("/user/getSpecificUser/{email}")
    UserDto getUser(@PathVariable("email") String email);

    @RequestMapping("/user/all")
    List<UserDto> getAllUsers();

    @RequestMapping("/user/checkUser/{email}")
    Boolean checkUser(@PathVariable("email") String email);

    @PostMapping("/user")
    UserDto createUser(@RequestBody UserCreateDto userCreateDto);
}
