package com.pds.neviabookingreminder.entities;


import javax.persistence.*;

@Entity
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_room", nullable = false)
    private Integer idRoom;

    @Column(name = "name")
    private String name;

    public RoomEntity() {
    }

    public RoomEntity(Integer idRoom, String name) {
        this.idRoom = idRoom;
        this.name = name;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}