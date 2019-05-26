package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingUserInfoDtoIn {
    @JsonProperty(required = true)
    private String bookingId;
    @JsonProperty(required = true)
    private String prevStatusId;

    public BookingUserInfoDtoIn() {
    }

    public BookingUserInfoDtoIn(String bookingId, String prevStatusId) {
        this.bookingId = bookingId;
        this.prevStatusId = prevStatusId;
    }

    public String getPrevStatusId() {
        return prevStatusId;
    }

    public void setPrevStatusId(String prevStatusId) {
        this.prevStatusId = prevStatusId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
