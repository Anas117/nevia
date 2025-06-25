package com.pds.neviabooking.services;

import com.pds.neviabooking.dto.BookingDto;
import com.pds.neviabooking.dto.RoomAvailableDto;
import com.pds.neviabooking.entities.BookingEntity;
import com.pds.neviabooking.entities.RoomEntity;
import com.pds.neviabooking.entities.UserEntity;
import com.pds.neviabooking.repositories.BookingRepository;
import com.pds.neviabooking.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RabbitMQProducerService rabbitMQProducerService;

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, RabbitMQProducerService rabbitMQProducerService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.rabbitMQProducerService = rabbitMQProducerService;
    }

    public List<BookingDto> selectBookingByUser(String email) {
        List<BookingEntity> bookingEntityList = bookingRepository.selectBookingByUser(email);
        List<BookingDto> bookingDtoList = new ArrayList<>();

        for (BookingEntity b : bookingEntityList) {
            bookingDtoList.add(new BookingDto(b.getIdUser().getEmail(), b.getIdUser().getFirstName(), b.getIdUser().getLastName(), b.getStartTime(), b.getEndTime(), b.getIdRoom().getName(), b.getIdRoom().getIdRoom(), b.getIdUser().getIdUser()));
        }

        log.info("Return list of booking by user " + email);
        return bookingDtoList;
    }

    public void insertBooking(BookingDto bookingDto) {
        UserEntity userEntity = new UserEntity(bookingDto.getIdUser(), bookingDto.getFirstName(), bookingDto.getLastName(), bookingDto.getEmail());
        RoomEntity roomEntity = new RoomEntity(bookingDto.getIdRoom(), bookingDto.getRoomName());

        // conversion add one hour, so we need to delete one hour
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(bookingDto.getStartTime().getTime());
        cal.add(Calendar.HOUR, -1);
        bookingDto.setStartTime(new Timestamp(cal.getTime().getTime()));

        cal.setTimeInMillis(bookingDto.getEndTime().getTime());
        cal.add(Calendar.HOUR, -1);
        bookingDto.setEndTime(new Timestamp(cal.getTime().getTime()));

        bookingRepository.save(new BookingEntity(userEntity, roomEntity, bookingDto.getStartTime(), bookingDto.getEndTime()));
        log.info("Save booking with user " + userEntity.getEmail() + " with room " + roomEntity.getName());
        rabbitMQProducerService.sendMessage(bookingDto);
    }

    public Page<RoomAvailableDto> selectAvailableRoom(Timestamp start_time, Timestamp end_time, Pageable pageable) {
        return roomRepository.selectAvailableRoom(start_time, end_time, pageable);
    }
}