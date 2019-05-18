package com.course.PhotoSocial.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ServiceDtoIn {

    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private double price;

    public ServiceDtoIn() {
    }

    public ServiceDtoIn(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDtoIn that = (ServiceDtoIn) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
