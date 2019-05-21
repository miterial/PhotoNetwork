package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingServiceDtoIn {
    @JsonProperty
    private String masterId;
    @JsonProperty
    private String serviceId;
    @JsonProperty
    private String datetime;

    public BookingServiceDtoIn() {
    }

    public BookingServiceDtoIn(String masterId, String serviceId, String datetime) {
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.datetime = datetime;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
