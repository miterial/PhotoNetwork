package com.course.PhotoSocial.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserServicesDtoIn {
    @JsonProperty
    List<ServiceDtoIn> services;
    @JsonProperty
    long userId;

    public UserServicesDtoIn() {
    }

    public UserServicesDtoIn(List<ServiceDtoIn> services, long userId) {
        this.services = services;
        this.userId = userId;
    }

    public List<ServiceDtoIn> getServices() {
        return services;
    }

    public void setServices(List<ServiceDtoIn> services) {
        this.services = services;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
