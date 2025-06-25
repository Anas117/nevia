package com.pds.dispatcher.dto;

public class RoomAvailableDto {

    private Integer idRoom;
    private String nameRoom;

    public RoomAvailableDto() {
    }

    public RoomAvailableDto(Integer idRoom, String nameRoom) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }
}
