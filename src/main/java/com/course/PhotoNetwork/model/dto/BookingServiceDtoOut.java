package com.course.PhotoNetwork.model.dto;

import com.course.PhotoNetwork.model.UserModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingServiceDtoOut {
    @JsonProperty
    private long master;
    @JsonProperty
    private String masterUsername;
    @JsonProperty
    private String datetime;

    public BookingServiceDtoOut() {
    }

}
