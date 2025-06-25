package com.pds.neviabooking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.neviabooking.dto.BookingDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class RabbitMQProducerService {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducerService.class);

    @Value("${rabbitmq.queue}")
    private String QUEUE;

    @Value("${spring.rabbitmq.host}")
    private String HOST;

    public void sendMessage(BookingDto bookingDto) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(bookingDto);
            byte[] data = json.getBytes(StandardCharsets.UTF_8);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .contentType("application/json")
                    .build();
            log.info("Send Message in rabbitmq of the booking with user " + bookingDto.getEmail() + " with room " + bookingDto.getRoomName());
            channel.basicPublish("", QUEUE, null, data);
            channel.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
