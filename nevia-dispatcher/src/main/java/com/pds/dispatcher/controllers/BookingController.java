package com.pds.dispatcher.controllers;

import com.pds.dispatcher.dto.BookingDto;
import com.pds.dispatcher.dto.RoomAvailableDto;
import com.pds.dispatcher.services.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/mybooking/{email}")
    public ResponseEntity<List<BookingDto>> selectBookingByUser(@NonNull @PathVariable("email") String email) {
        log.info("Select booking by user "+ email);
        return Optional.ofNullable(bookingService.selectBookingByUser(email))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/book")
    public ResponseEntity<Void> insertBook(@RequestBody BookingDto bookingDto) {
        bookingService.insertBooking(bookingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/availableRoom")
    public ResponseEntity<Page<RoomAvailableDto>> selectAvailableRoom(@NonNull @RequestParam("start") String start, @NonNull @RequestParam("end") String end, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return Optional.ofNullable(bookingService.selectAvailableRoom(Timestamp.valueOf(start), Timestamp.valueOf(end), pageable))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}