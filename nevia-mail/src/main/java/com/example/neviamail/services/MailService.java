package com.example.neviamail.services;
import com.example.neviamail.dto.BookingDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class MailService {

    private final MailSender mailSender;

    public MailService() {
        this.mailSender = new MailSender();
    }

    public void sendSimpleMessage(BookingDto reservation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@nevia.com");
        message.setTo(reservation.getEmail());
        message.setSubject(reservation.getRoomName());
        message.setText("Vous avez reserve la salle "+reservation.getRoomName()+" de "+reservation.getStartTime()+" à "+reservation.getEndTime()+" au nom de "+reservation.getFirstName()+" "+reservation.getLastName()+".");
        mailSender.getJavaMailSender().send(message);
    }
}
