package com.pds.nevianotificationmanager.services;

import com.pds.nevianotificationmanager.dto.BookingDto;
import com.pds.nevianotificationmanager.dto.firebase.PushNotificationRequest;
import com.pds.nevianotificationmanager.redis.model.Booking;
import com.pds.nevianotificationmanager.redis.model.Registration;
import com.pds.nevianotificationmanager.redis.repo.BookingRepository;
import com.pds.nevianotificationmanager.redis.repo.RegistrationRepository;
import com.pds.nevianotificationmanager.services.firebase.PushNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BookingService {

    private final RegistrationRepository registrationRepository;
    private final BookingRepository bookingRepository;
    private final PushNotificationService pushNotificationService;

    public BookingService(RegistrationRepository registrationRepository, BookingRepository bookingRepository, PushNotificationService pushNotificationService) {
        this.registrationRepository = registrationRepository;
        this.bookingRepository = bookingRepository;
        this.pushNotificationService = pushNotificationService;
    }

    public void receiveBooking(BookingDto reservation,String message) {
        Optional<Registration> userRegistration = registrationRepository.findById(reservation.getEmail());
        if(userRegistration.isPresent()){
            pushNotificationService.sendPushNotificationWithToken(new PushNotificationRequest("Notification de réservation",message,userRegistration.get().getToken()));
            log.info("Message sent to "+reservation.getEmail());
        }
        else {
            Optional<Booking> userBookings = bookingRepository.findById(reservation.getEmail());
            if(userBookings.isPresent()){
                userBookings.get().getMessages().add(message);
                System.out.println(userBookings.get().getMessages().toString()+"|"+userBookings.get().getMessages().size());
            }
            else {
                Booking b = new Booking(reservation.getEmail());
                b.getMessages().add(message);
                bookingRepository.save(b);
                log.info(b.getMessages().toString());
            }
            log.info("Saved message to emergency cache");
        }
    }
}