package com.pds.neviabookingreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NeviaBookingReminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeviaBookingReminderApplication.class, args);
    }

}
