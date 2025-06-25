package com.pds.nevianotificationmanager.services;

import com.pds.nevianotificationmanager.dto.firebase.PushNotificationRequest;
import com.pds.nevianotificationmanager.dto.firebase.RegistrationTokenDto;
import com.pds.nevianotificationmanager.redis.model.Booking;
import com.pds.nevianotificationmanager.redis.model.Registration;
import com.pds.nevianotificationmanager.redis.repo.BookingRepository;
import com.pds.nevianotificationmanager.redis.repo.RegistrationRepository;

import com.pds.nevianotificationmanager.services.firebase.PushNotificationService;
import com.pds.nevianotificationmanager.services.rabbitmq.RabbitMQConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationRepository registrationRepository;
    private final BookingRepository bookingRepository;
    private final PushNotificationService pushNotificationService;

    public RegistrationService(RegistrationRepository registrationRepository, BookingRepository bookingRepository, PushNotificationService pushNotificationService) {
        this.registrationRepository = registrationRepository;
        this.bookingRepository = bookingRepository;
        this.pushNotificationService = pushNotificationService;
    }

    public void register(RegistrationTokenDto registrationTokenDto){
        Registration registeredUser = new Registration(registrationTokenDto.getEmail(),registrationTokenDto.getToken());
        registrationRepository.save(registeredUser);
        Optional<Booking> userBookings = bookingRepository.findById(registrationTokenDto.getEmail());
        if(userBookings.isPresent()){
            for(String message : userBookings.get().getMessages()) {
                pushNotificationService.sendPushNotificationWithToken(new PushNotificationRequest("Notification de réservation",message,registeredUser.getToken()));
            }
            bookingRepository.delete(userBookings.get());
            log.info("Delete message to emergency cache");
        }
        log.info("Successfully registered");
    }

    public String unregister(String email){
        registrationRepository.deleteById(email);
        log.info("Successfully unregistered");
        return "Successfully unregistered";
    }

}
