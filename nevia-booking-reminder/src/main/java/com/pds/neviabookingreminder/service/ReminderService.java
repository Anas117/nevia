package com.pds.neviabookingreminder.service;

import com.pds.neviabookingreminder.redis.model.Booking;
import com.pds.neviabookingreminder.entities.BookingEntity;
import com.pds.neviabookingreminder.repositories.BookingRepository;
import com.pds.neviabookingreminder.redis.redisrepositories.RedisBookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReminderService {

    private static Integer delay = 3600;
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final RedisBookingRepository redisBookingRepository;
    private final RabbitMQProducerService rabbitMQProducerService;

    public ReminderService(BookingRepository bookingRepository, RedisBookingRepository redisBookingRepository, RabbitMQProducerService rabbitMQProducerService) {
        this.bookingRepository = bookingRepository;
        this.redisBookingRepository = redisBookingRepository;
        this.rabbitMQProducerService = rabbitMQProducerService;
    }

    public void setDelay(Integer newDelay){
        delay = newDelay;
        emptyCache();
    }

    public void emptyCache(){
        redisBookingRepository.deleteAll();
    }

    @Scheduled(fixedRate = 10000)
    public void checkUpcomingBookings() {
        List<BookingEntity> upcomingBookings = bookingRepository.checkUpcomingBookings(delay);
        log.info(upcomingBookings.size()+" bookings coming in "+delay+"s or less.");
        upcomingBookings.forEach(bookingEntity -> {
            if(!redisBookingRepository.existsById(bookingEntity.getIdBooking())) {
                rabbitMQProducerService.sendMessage(bookingEntity.toDto());
                redisBookingRepository.save(new Booking(bookingEntity.getIdBooking(),bookingEntity.getIdUser().getIdUser(),bookingEntity.getIdRoom().getIdRoom(),bookingEntity.getStartTime().toString(),bookingEntity.getEndTime().toString()));
            }
        });
    }

    @Scheduled(initialDelay=86400000, fixedRate=86400000)
    public void emptyEachDay() {
        emptyCache();
    }
}
