package com.course.PhotoNetwork.model.dto;


import com.course.PhotoNetwork.controller.validation.Birthday;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserDtoIn {

    @JsonProperty
    private Long id;

    @JsonProperty
    @Email(message = "Некорректный Email")
    @NotBlank(message = "Заполните поле Email")
    private String email;

    @JsonProperty
    @Length(min=6, message = "Пароль должен быть не короче 6 символов")
    private String password;

    @JsonProperty
    @NotBlank(message = "Заполните поле Login")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Только цифры и латинские буквы")
    private String username;

    @JsonProperty
    @Pattern(regexp = "[a-zA-Z]*", message = "Только буквы")
    private String name;
    @JsonProperty
    @Pattern(regexp = "[a-zA-Z]*", message = "Только буквы")
    private String surname;

    @JsonProperty
    @Birthday
    private String birthday;

    @JsonProperty
    private boolean provideServices;
    @JsonProperty
    private MultipartFile avatar;
    @JsonProperty
    private String description;

    @JsonProperty
    private Set<ServiceDto> services;

    public UserDtoIn(){}

    public UserDtoIn(String email, String password, String name, String surname, String username, String birthday, MultipartFile avatar, String description) {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ServiceDto> getServices() {
        return services;
    }

    public void setServices(Set<ServiceDto> services) {
        this.services = services;
    }
}
