package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private String regdate;
    @JsonProperty
    private String username;
    @JsonProperty
    private String description;
    @JsonProperty
    private String email;
    @JsonProperty
    private String avatar;
    @JsonProperty
    private double avgRate;
    @JsonProperty
    private List<ServiceDto> services;
    @JsonProperty
    private boolean provideServices;
    @JsonProperty
    private List<ReviewDtoOut> reviews;
    @JsonProperty
    private List<PhotoDtoOutSmall> photos;

    public UserDtoOut() { }

    public UserDtoOut(long id, String name, String surname, Date birthday, Date regdate, String username, String description, String email, String avatar, double avgRate, List<ServiceDto> services, boolean provideServices, List<ReviewDtoOut> reviews, List<PhotoDtoOutSmall> photos) throws ParseException {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.description = description;
        this.email = email;
        this.avgRate = avgRate;
        this.provideServices = provideServices;
        this.reviews = reviews;
        this.photos = photos;
        this.avatar = avatar;
        this.services = services;

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        this.regdate = df.format(regdate);
        if(birthday != null) {
            this.birthday = df.format(birthday);
        }
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

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
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

    public List<ReviewDtoOut> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDtoOut> reviews) {
        this.reviews = reviews;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoOut that = (UserDtoOut) o;
        return id == that.id &&
                Double.compare(that.avgRate, avgRate) == 0 &&
                provideServices == that.provideServices &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(birthday, that.birthday) &&
                regdate.equals(that.regdate) &&
                username.equals(that.username) &&
                Objects.equals(description, that.description) &&
                email.equals(that.email) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(services, that.services) &&
                Objects.equals(reviews, that.reviews) &&
                Objects.equals(photos, that.photos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birthday, regdate, username, description, email, avatar, avgRate, services, provideServices, reviews, photos);
    }
}
