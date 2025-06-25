package com.pds.nevianotificationmanager.redis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RedisHash("Booking")
public class Booking implements Serializable {

    @Id
    private String email;
    private List<String> messages;

    public Booking(String email){
        this.email=email;
        this.messages=new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "email='" + email + '\'' +
                ", messages=" + messages.toString() +
                '}';
    }
}
