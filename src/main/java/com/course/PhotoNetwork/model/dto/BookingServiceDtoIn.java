package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class BookingServiceDtoIn {
    @JsonProperty
    private long masterId;
    @JsonProperty
    private long serviceId;
    @JsonProperty
    private Date date;

    public BookingServiceDtoIn() {
    }

    public BookingServiceDtoIn(long masterId, long serviceId, Date date) {
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.date = date;
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
