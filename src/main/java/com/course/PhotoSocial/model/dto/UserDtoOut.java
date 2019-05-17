package com.course.PhotoSocial.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDtoOut {

    @JsonProperty
    private String name;
    @JsonProperty
    private String surname;
    @JsonProperty
    private Date birthday;
    @JsonProperty
    private Date regdate;
    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private String avatar;
    @JsonProperty
    private List<RoleDtoOut> roles;

    public UserDtoOut() { }

    public UserDtoOut(String name, String surname, Date birthday, Date regdate, String username, String email, String avatar, List<RoleDtoOut> roles) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.regdate = regdate;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<RoleDtoOut> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDtoOut> roles) {
        this.roles = roles;
    }
}
