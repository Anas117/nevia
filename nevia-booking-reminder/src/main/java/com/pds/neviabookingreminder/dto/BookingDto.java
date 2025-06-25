package com.pds.neviabookingreminder.dto;


import java.io.Serializable;
import java.sql.Timestamp;


public class BookingDto implements Serializable {
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp startTime;
    private Timestamp endTime;
    private String roomName;
    private Integer idRoom;
    private Integer idUser;

    public BookingDto(String email, String firstName, String lastName, Timestamp startTime, Timestamp endTime, String roomName, Integer idRoom, Integer idUser) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomName = roomName;
        this.idRoom = idRoom;
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}

