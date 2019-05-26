package com.course.PhotoNetwork.model.dto;

import com.course.PhotoNetwork.model.types.BookingEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingServiceDtoOut {
    @JsonProperty
    private Long bookingId;
    @JsonProperty
    private Long masterId;
    @JsonProperty
    private String masterUsername;
    @JsonProperty
    private Long clientId;
    @JsonProperty
    private String clientUsername;
    @JsonProperty
    private Long serviceId;
    @JsonProperty
    private String serviceName;
    @JsonProperty
    private Double price;
    @JsonProperty
    private String datetime;
    @JsonProperty
    private BookingEnum status;

    @JsonProperty
    private boolean finishedByMaster;
    @JsonProperty
    private boolean finishedByClient;
    @JsonProperty
    private boolean deletedByMaster;
    @JsonProperty
    private boolean deletedByClient;

    public BookingServiceDtoOut() {
    }

    public BookingServiceDtoOut(Long bookingId, Long masterId, String masterUsername, Long clientId, String clientUsername, Long serviceId, String serviceName, Double price, String datetime, BookingEnum status, boolean finishedByMaster, boolean finishedByClient, boolean deletedByMaster, boolean deletedByClient) {
        this.bookingId = bookingId;
        this.masterId = masterId;
        this.masterUsername = masterUsername;
        this.clientId = clientId;
        this.clientUsername = clientUsername;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.datetime = datetime;
        this.status = status;
        this.finishedByMaster = finishedByMaster;
        this.finishedByClient = finishedByClient;
        this.deletedByMaster = deletedByMaster;
        this.deletedByClient = deletedByClient;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public String getMasterUsername() {
        return masterUsername;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Double getPrice() {
        return price;
    }

    public String getDatetime() {
        return datetime;
    }

    public BookingEnum getStatus() {
        return status;
    }

    public boolean isFinishedByMaster() {
        return finishedByMaster;
    }

    public boolean isFinishedByClient() {
        return finishedByClient;
    }

    public boolean isDeletedByMaster() {
        return deletedByMaster;
    }

    public boolean isDeletedByClient() {
        return deletedByClient;
    }
}
