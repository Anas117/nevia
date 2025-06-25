package com.pds.nevianotificationmanager.redis.repo;

import com.pds.nevianotificationmanager.redis.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookingRepository  extends CrudRepository<Booking, String> {

}