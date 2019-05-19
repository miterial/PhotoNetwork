package com.course.PhotoNetwork.model.dto;

public class RoleDtoOut {
    private String name;

    public RoleDtoOut(String rolename) {
        this.name = rolename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
