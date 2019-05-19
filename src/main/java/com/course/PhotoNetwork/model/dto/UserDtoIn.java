package com.course.PhotoNetwork.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

public class UserDtoIn {
    @JsonProperty(required = true)
    private String email;
    @JsonProperty(required = true)
    private String password;
    @JsonProperty
    private String name;
    @JsonProperty
    private String surname;
    @JsonProperty
    private String username;
    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @JsonProperty
    private boolean provideServices;
    @JsonProperty
    private MultipartFile avatar;
    @JsonProperty
    private String description;

    public UserDtoIn(){}

    public UserDtoIn(String email, String password, String name, String surname, String username, Date birthday, MultipartFile avatar, String description) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.birthday = birthday;
        this.avatar = avatar;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isProvideServices() {
        return provideServices;
    }

    public void setProvideServices(boolean provideServices) {
        this.provideServices = provideServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoIn userDtoIn = (UserDtoIn) o;
        return email.equals(userDtoIn.email) &&
                password.equals(userDtoIn.password) &&
                Objects.equals(name, userDtoIn.name) &&
                Objects.equals(surname, userDtoIn.surname) &&
                Objects.equals(username, userDtoIn.username) &&
                Objects.equals(birthday, userDtoIn.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, surname, username, birthday);
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
