package com.pds.nevianotificationmanager.controller;

import com.pds.nevianotificationmanager.controller.firebase.RegistrationController;
import com.pds.nevianotificationmanager.dto.BookingDto;
import com.pds.nevianotificationmanager.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private static final Logger log = LoggerFactory.getLogger(MailController.class);


    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public void send(@RequestBody BookingDto reservation) {
        log.info("Send message to " + reservation.getEmail());
        mailService.sendSimpleMessage(reservation);
    }

}
