package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookingUserInfoDtoOut {
    @JsonProperty
    private long userId;
    @JsonProperty
    List<BookingServiceDtoOut> booked;
    @JsonProperty
    List<BookingServiceDtoOut> upcoming;

    public BookingUserInfoDtoOut(long userId, List<BookingServiceDtoOut> booked, List<BookingServiceDtoOut> upcoming) {
        this.userId = userId;
        this.booked = booked;
        this.upcoming = upcoming;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<BookingServiceDtoOut> getBooked() {
        return booked;
    }

    public void setBooked(List<BookingServiceDtoOut> booked) {
        this.booked = booked;
    }

    public List<BookingServiceDtoOut> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<BookingServiceDtoOut> upcoming) {
        this.upcoming = upcoming;
    }
}
