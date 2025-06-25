package com.pds.dispatcher.services;

import com.pds.dispatcher.controllers.HealthController;
import com.pds.dispatcher.dto.UserCreateDto;
import com.pds.dispatcher.dto.UserDto;
import com.pds.dispatcher.feign.ServiceUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final ServiceUser serviceUser;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public List<UserDto> getAllUsers(){
        log.info("Get all users ");
        return serviceUser.getAllUsers();
    }

    public UserDto getUser(String email){
        return serviceUser.getUser(email);
    }

    public UserDto createUser(UserCreateDto userCreateDto){
        log.info("Create user "+ userCreateDto.getFirstName() + " - "+ userCreateDto.getLastName());
        return serviceUser.createUser(userCreateDto);
    }

    public Boolean checkUser(String email){
        log.info("Check user "+ email);
        return serviceUser.checkUser(email);
    }
}
