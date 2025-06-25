package com.pds.neviabookingreminder.repositories;

import com.pds.neviabookingreminder.entities.BookingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {


    @Query(value= "select * from booking where EXTRACT(EPOCH FROM (start_time - current_timestamp)) < ?1 and EXTRACT(EPOCH FROM (start_time - current_timestamp)) > 0",nativeQuery = true)
    List<BookingEntity> checkUpcomingBookings(Integer delay);
}