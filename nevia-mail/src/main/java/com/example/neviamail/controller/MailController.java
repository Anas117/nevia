package com.example.neviamail.controller;

import com.example.neviamail.dto.BookingDto;
import com.example.neviamail.services.MailService;
import io.micrometer.core.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/mail")
public class MailController {


    private final MailService mailService;
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public void send(@RequestBody BookingDto reservation){
        //ReservationInfoDto reservation2 = new ReservationInfoDto("anas.altundag@gmail.com","Anas","Altundag",new Timestamp(2022,11,18,17,00,00,0),new Timestamp(2022,11,18,17,00,00,0),"Info 2");
        mailService.sendSimpleMessage(reservation);
    }

}
