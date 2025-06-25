package com.pds.neviauser.repositories;

import com.pds.neviauser.dto.UserDto;
import com.pds.neviauser.entities.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    UserEntity getUserByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users(firstname, lastname, email) VALUES (?1, ?2, ?3);", nativeQuery = true)
    void insertUser(String firstName, String lastName, String email);


    @Query(value = "SELECT * FROM users WHERE id_user = ?1", nativeQuery = true)
    UserEntity getUserById(Integer id);

    @Query(value = "SELECT 1 FROM users WHERE email = ?1", nativeQuery = true)
    Integer checkUser(String email);

    @Query(value = "SELECT users.id_user, users.firstname, users.lastname, users.email FROM users", nativeQuery = true)
    List<UserEntity> getAll();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users WHERE id = ?1", nativeQuery = true)
    void deleteUser(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET name = ?2 WHERE id = ?1", nativeQuery = true)
    void modifyName(Integer id, String s);
}
