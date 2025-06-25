package com.pds.neviabookingreminder.entities;


import com.pds.neviabookingreminder.dto.BookingDto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "booking")
public class BookingEntity {
    @Id
    @SequenceGenerator(name = "booking_entity_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_entity_seq")
    @Column(name = "id_booking", nullable = false, precision = 131089)
    private Integer idBooking;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_user")
    private UserEntity idUser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_room")
    private RoomEntity idRoom;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    public BookingEntity() {
    }

    public BookingEntity(UserEntity idUser, RoomEntity idRoom, Timestamp startTime, Timestamp endTime) {
        this.idUser = idUser;
        this.idRoom = idRoom;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BookingDto toDto(){
        return new BookingDto(idUser.getEmail(),idUser.getFirstName(),idUser.getLastName(),startTime,endTime,idRoom.getName(),idRoom.getIdRoom(),idUser.getIdUser());
    }

    public Integer getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(Integer idBooking) {
        this.idBooking = idBooking;
    }

    public UserEntity getIdUser() {
        return idUser;
    }

    public void setIdUser(UserEntity idUser) {
        this.idUser = idUser;
    }

    public RoomEntity getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(RoomEntity idRoom) {
        this.idRoom = idRoom;
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
}
