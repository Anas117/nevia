package com.pds.dispatcher.feign;


import com.pds.dispatcher.dto.BookingDto;
import com.pds.dispatcher.dto.RoomAvailableDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

@FeignClient(name = "booking-service")
//@RibbonClient(name = "booking-service")
public interface ServiceBooking {

    @RequestMapping(value="/booking/book", method = RequestMethod.POST)
    void insertBooking(@RequestBody BookingDto bookingDto);

    @RequestMapping(value = "/booking/mybooking/{email}")
    List<BookingDto> selectBookingByUser(@PathVariable String email);

    @RequestMapping("/booking/roomAvailable")
    Page<RoomAvailableDto> selectAvailableRoom(@RequestParam("start") Timestamp start, @RequestParam("end") Timestamp end, Pageable pageable);

}
