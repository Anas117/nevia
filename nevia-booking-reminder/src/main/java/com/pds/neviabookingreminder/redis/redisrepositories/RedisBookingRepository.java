package com.pds.neviabookingreminder.redis.redisrepositories;

import com.pds.neviabookingreminder.redis.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("redisBookingRepository")
public interface RedisBookingRepository extends CrudRepository<Booking, Integer> {
}
