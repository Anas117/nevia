package com.pds.dispatcher.dto;

public class StatusHealthDto {

    private String status;

    public StatusHealthDto() {
    }

    public StatusHealthDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
