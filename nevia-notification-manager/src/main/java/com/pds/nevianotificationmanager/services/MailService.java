package com.pds.nevianotificationmanager.services;

import com.pds.nevianotificationmanager.dto.BookingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private final MailSender mailSender;

    public MailService() {
        this.mailSender = new MailSender();
    }

    public void sendSimpleMessage(BookingDto reservation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@nevia.com");
        message.setTo(reservation.getEmail());
        message.setSubject(reservation.getRoomName());
        message.setText("Vous avez reserve la salle " + reservation.getRoomName() + " de " + reservation.getStartTime() + " à " + reservation.getEndTime() + " au nom de " + reservation.getFirstName() + " " + reservation.getLastName() + ".");
        mailSender.getJavaMailSender().send(message);
        log.info("Sent message to " + reservation.getEmail());
    }
}
