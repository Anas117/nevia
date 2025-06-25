package com.pds.neviauser.services;

import com.pds.neviauser.dto.UserCreateDto;
import com.pds.neviauser.dto.UserDto;
import com.pds.neviauser.entities.UserEntity;
import com.pds.neviauser.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAllUsers() {
        return repository.getAll();
    }

    public UserEntity getUser(String email) {
        return repository.getUserByEmail(email);
    }

    public Boolean checkUser(String email) {
        return repository.checkUser(email) != null;
    }

    public UserDto createUser(UserCreateDto userCreateDto) {
        repository.save(new UserEntity(userCreateDto.getFirstName(), userCreateDto.getLastName(), userCreateDto.getEmail()));
        log.info("Creation user : " + userCreateDto.getFirstName() + " - " + userCreateDto.getLastName());

        UserEntity user = repository.getUserByEmail(userCreateDto.getEmail());
        return new UserDto(user.getIdUser(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public void deleteUser(Integer id) {
        log.info("Delete user : " + id);
        repository.deleteUser(id);
    }

    public void updateName(Integer id, String name) {
        log.info("Update name of user " + id + " with the value  : " + name);
        repository.modifyName(id, name);
    }
}
