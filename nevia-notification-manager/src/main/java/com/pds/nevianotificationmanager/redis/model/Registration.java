package com.pds.nevianotificationmanager.redis.model;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Registration")
public class Registration implements Serializable {

    @Id
    private String email;
    private String token;

    public Registration(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegistrationToken{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}
