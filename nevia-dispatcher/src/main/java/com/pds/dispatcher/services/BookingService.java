package com.pds.dispatcher.services;


import com.pds.dispatcher.dto.BookingDto;
import com.pds.dispatcher.dto.RoomAvailableDto;
import com.pds.dispatcher.feign.ServiceBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookingService {
    private final ServiceBooking serviceBooking;
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);


    public BookingService(ServiceBooking serviceBooking) {
        this.serviceBooking = serviceBooking;
    }

    public List<BookingDto> selectBookingByUser(String email) {
        return serviceBooking.selectBookingByUser(email);
    }

    public void insertBooking(BookingDto bookingDto) {
        log.info("Insert booking with user " + bookingDto.getEmail() + " and room " + bookingDto.getRoomName());
        serviceBooking.insertBooking(bookingDto);
    }

    public Page<RoomAvailableDto> selectAvailableRoom(Timestamp start, Timestamp end, Pageable pageable) {
        log.info("Select available Room from  " + start + " and end " + end);
        return serviceBooking.selectAvailableRoom(start, end, pageable);
    }
}
