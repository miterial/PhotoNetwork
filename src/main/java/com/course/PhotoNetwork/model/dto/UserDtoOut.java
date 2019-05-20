package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
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
    private String birthday;
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
    private List<ServiceDto> services;
    @JsonProperty
    private boolean provideServices;
    @JsonProperty
    private List<PhotoDtoOutSmall> photos;

    public UserDtoOut() { }

    public UserDtoOut(long id, String name, String surname, Date birthday, Date regdate, String username, String description, String email, String avatar, List<ServiceDto> services, boolean provideServices, List<PhotoDtoOutSmall> photos) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.regdate = regdate;
        this.username = username;
        this.description = description;
        this.email = email;
        this.provideServices = provideServices;
        this.photos = photos;
        this.password = "";
        this.avatar = avatar;
        this.services = services;

        if(birthday != null)
            this.birthday = birthday.toString();
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public List<ServiceDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceDto> services) {
        this.services = services;
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

    public List<PhotoDtoOutSmall> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDtoOutSmall> photos) {
        this.photos = photos;
    }
}
