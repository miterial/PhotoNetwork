package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class UserDtoOut {

    @JsonProperty
    private long id;
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
    private String description;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
    @JsonProperty
    private String avatar;
    @JsonProperty
    private List<RoleDtoOut> roles;
    @JsonProperty
    private boolean provideServices;

    public UserDtoOut() { }

    public UserDtoOut(long id, String name, String surname, Date birthday, Date regdate, String username, String description, String email, String avatar, List<RoleDtoOut> roles, boolean provideServices) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.regdate = regdate;
        this.username = username;
        this.description = description;
        this.email = email;
        this.provideServices = provideServices;
        this.password = "";
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isProvideServices() {
        return provideServices;
    }

    public void setProvideServices(boolean provideServices) {
        this.provideServices = provideServices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
