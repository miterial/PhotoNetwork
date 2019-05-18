package com.course.PhotoSocial.model;

public enum ServiceEnum {
    SERVICE_ONE("сервис1"),
    SERVICE_TWO("сервис2");

    private String val;
    ServiceEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
