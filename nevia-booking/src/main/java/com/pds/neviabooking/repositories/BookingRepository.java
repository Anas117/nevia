package com.pds.neviabooking.repositories;

import com.pds.neviabooking.entities.BookingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {


    @Query(value= """
           select b
           from BookingEntity as b where b.idUser.email = :email
           """)
    List<BookingEntity> selectBookingByUser(String email);
}