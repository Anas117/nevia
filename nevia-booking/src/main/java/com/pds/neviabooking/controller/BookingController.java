package com.pds.neviabooking.controller;

import com.pds.neviabooking.dto.BookingDto;
import com.pds.neviabooking.dto.RoomAvailableDto;
import com.pds.neviabooking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/book")
    public void insertBooking(@RequestBody BookingDto bookingDto) {
        bookingService.insertBooking(bookingDto);
    }

    @GetMapping("/mybooking/{email}")
    public List<BookingDto> selectBookingByUser(@PathVariable String email) {
        return bookingService.selectBookingByUser(email);
    }

    @GetMapping("/roomAvailable")
    public Page<RoomAvailableDto> selectAvailableRoom(@RequestParam("start") Timestamp start, @RequestParam("end") Timestamp end, Pageable pageable) {
        return bookingService.selectAvailableRoom(start, end, pageable);
    }

}
