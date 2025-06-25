package com.pds.nevianotificationmanager.redis.repo;

import com.pds.nevianotificationmanager.redis.model.Registration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RegistrationRepository  extends CrudRepository<Registration, String> {


    Optional<Registration> findByEmail(String email);

}