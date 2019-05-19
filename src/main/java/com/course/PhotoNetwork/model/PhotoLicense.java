package com.course.PhotoNetwork.model;

public enum PhotoLicense {
    TRADITIONAL("Traditional Copyright"),
    CREATIVE_COMMONS("Creative Commons"),
    PUBLIC_DOMAIN("Public Domain");

    private String val;
    PhotoLicense(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

}
