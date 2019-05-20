package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BookingServiceDtoIn {
    @JsonProperty
    private String masterId;
    @JsonProperty
    private String serviceId;
    @JsonProperty
    private String date;

    public BookingServiceDtoIn() {
    }

    public BookingServiceDtoIn(String masterId, String serviceId, String date) {
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.date = date;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
