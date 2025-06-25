package com.pds.neviabooking;

import com.pds.neviabooking.dto.BookingDto;
import com.pds.neviabooking.entities.BookingEntity;
import com.pds.neviabooking.repositories.BookingRepository;
import com.pds.neviabooking.repositories.RoomRepository;
import com.pds.neviabooking.services.BookingService;
import com.pds.neviabooking.services.RabbitMQProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import static org.mockito.Mockito.verify;

@ActiveProfiles("localhost")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class NeviaBookingApplicationTests {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RabbitMQProducerService rabbitMQProducerService;

    private BookingService bookingService;

    @BeforeEach
    void setUp(){
        this.bookingService = new BookingService(bookingRepository,roomRepository,rabbitMQProducerService);
    }
    @Test
    void bookingCountIncrement(){
        int beginCount = bookingRepository.selectBookingByUser("toto.com").size()+1;
        bookingService.insertBooking(new BookingDto("toto.com","toto","toto",Timestamp.valueOf("2023-02-25 10:10:10.0"),Timestamp.valueOf("2023-02-25 12:10:10.0"),"Ngelak",1,9));
        int endCount = bookingRepository.selectBookingByUser("toto.com").size();
        Assertions.assertEquals(beginCount,endCount);
    }
    @Test
    void availableRoomCountDecrement(){
        int beginCount = bookingService.selectAvailableRoom(Timestamp.valueOf("2023-02-25 10:10:10.0"),Timestamp.valueOf("2023-02-25 12:10:10.0"),PageRequest.of(0, 20)).getSize();
        bookingService.insertBooking(new BookingDto("toto.com","toto","toto",Timestamp.valueOf("2023-02-25 10:10:10.0"),Timestamp.valueOf("2023-02-25 12:10:10.0"),"Ngelak",1,9));
        int endCount = bookingService.selectAvailableRoom(Timestamp.valueOf("2023-02-25 10:10:10.0"),Timestamp.valueOf("2023-02-25 12:10:10.0"),PageRequest.of(0, 20)).getSize();
        Assertions.assertEquals(beginCount,endCount-1);
    }
}
