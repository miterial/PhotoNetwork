package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ServiceDto {

    @JsonProperty
    private long id;
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private double price;

    public ServiceDto() {
    }

    public ServiceDto(long id, String name, double price) {
        this.id = id;
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
        ServiceDto that = (ServiceDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
