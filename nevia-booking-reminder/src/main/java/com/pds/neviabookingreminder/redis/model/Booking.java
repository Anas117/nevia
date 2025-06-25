package com.pds.neviabookingreminder.redis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RedisHash("ReminderBooking")
public class Booking implements Serializable {

    @Id
    private Integer id_booking;
    private Integer id_user;
    private Integer id_room;
    private String start_time;
    private String end_time;

    public Booking(Integer id_booking, Integer id_user, Integer id_room, String start_time, String end_time) {
        this.id_booking = id_booking;
        this.id_user = id_user;
        this.id_room = id_room;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
