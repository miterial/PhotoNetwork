package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class UserServicesDto {
    @JsonProperty
    Set<ServiceDto> services;
    @JsonProperty
    Long userId;

    public UserServicesDto() {
    }

    public UserServicesDto(Set<ServiceDto> services, Long userId) {
        this.services = services;
        this.userId = userId;
    }

    public Set<ServiceDto> getServices() {
        return services;
    }

    public void setServices(Set<ServiceDto> services) {
        this.services = services;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
