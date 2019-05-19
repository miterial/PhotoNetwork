package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserServicesDtoIn {
    @JsonProperty
    List<ServiceDto> services;
    @JsonProperty
    long userId;

    public UserServicesDtoIn() {
    }

    public UserServicesDtoIn(List<ServiceDto> services, long userId) {
        this.services = services;
        this.userId = userId;
    }

    public List<ServiceDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceDto> services) {
        this.services = services;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
