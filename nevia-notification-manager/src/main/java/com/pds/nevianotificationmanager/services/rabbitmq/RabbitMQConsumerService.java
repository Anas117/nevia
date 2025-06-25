package com.pds.nevianotificationmanager.services.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.nevianotificationmanager.dto.BookingDto;
import com.pds.nevianotificationmanager.services.BookingService;
import com.pds.nevianotificationmanager.services.MailService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitMQConsumerService {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumerService.class);

    @Value("${rabbitmq.queue}")
    private String BookingQUEUE;

    @Value("${rabbitmq.queue2}")
    private String ReminderQUEUE;

    @Value("${spring.rabbitmq.host}")
    private String HOST;

    private final BookingService bookingService;
    private final MailService mailService;


    public RabbitMQConsumerService(BookingService bookingService, MailService mailService) {
        this.bookingService = bookingService;
        this.mailService = mailService;
    }

    @Scheduled(fixedRate = 50)
    public void receiveBooking() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                BookingDto reservation = mapper.readValue(message, BookingDto.class);
                log.info("Received from queue : " + reservation.toString());
                String notif = "Vous avez réservé la salle "+reservation.getRoomName()+" au nom de "+reservation.getLastName()+" pour le "+reservation.getStartTime()+".";
                bookingService.receiveBooking(reservation,notif);
                mailService.sendSimpleMessage(reservation);
            };
            channel.basicConsume(BookingQUEUE, true, deliverCallback, consumerTag -> { });
            channel.basicConsume(ReminderQUEUE, true, deliverCallback, consumerTag -> { });
            channel.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    @Scheduled(fixedRate = 50)
    public void receiveReminder() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                BookingDto reservation = mapper.readValue(message, BookingDto.class);
                log.info("Received from queue : " + reservation.toString());
                String notif = "Rappel: Vous avez réservé la salle "+reservation.getRoomName()+" au nom de "+reservation.getLastName()+" pour le "+reservation.getStartTime()+".";
                bookingService.receiveBooking(reservation,notif);
            };
            channel.basicConsume(ReminderQUEUE, true, deliverCallback, consumerTag -> { });
            channel.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/
    /*
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveBooking(BookingDto reservation) {
        String message = "Vous avez réservé la salle "+reservation.getRoomName()+" au nom de "+reservation.getLastName()+" pour le "+reservation.getStartTime()+".";
        bookingService.receiveBooking(reservation,message);
        mailService.sendSimpleMessage(reservation);
    }

    @RabbitListener(queues = "${rabbitmq.queue2}")
    public void receiveReminder(BookingDto reservation) {
        String message = "Rappel: Vous avez réservé la salle "+reservation.getRoomName()+" au nom de "+reservation.getLastName()+" pour le "+reservation.getStartTime()+".";
        bookingService.receiveBooking(reservation,message);
    }*/
}
