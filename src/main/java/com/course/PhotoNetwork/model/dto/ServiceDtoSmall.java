package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceDtoSmall {
    @JsonProperty
    private String name;

    public ServiceDtoSmall(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
