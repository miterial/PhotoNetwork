package com.course.PhotoNetwork.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private long id;

    private String name;

    private String surname;

    private Date birthday;

    private Date regdate = Calendar.getInstance().getTime();

    @NotBlank(message = "Заполните поле Login")
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @Length(min=6, message = "Пароль должен быть не короче 6 символов")
    private String password;

    @Column(unique = true, nullable = false)
    @Email(message = "Некорректный Email")
    private String email;

    @Lob
    @Column(length = 3000)
    private String avatar;

    private String description;

    @Column
    private double avgRate;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<RoleModel> roles;

    @OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
    private Set<ServiceModel> services;

    @ManyToMany
    private List<UserModel> subscribers;

    @OneToMany(mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
    private List<PhotoModel> photos;

    @OneToMany(mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH, org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
    private List<LikeModel> likes;

    @OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
    private List<ReviewModel> reviews;  //if user provides services

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getRegdate() {
        return regdate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }


    public Set<ServiceModel> getServices() {
        return services;
    }

    public void setServices(Set<ServiceModel> services) {
        this.services = services;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.surname);
        hash = 79 * hash + Objects.hashCode(this.birthday);
        hash = 79 * hash + Objects.hashCode(this.regdate);
        hash = 79 * hash + Objects.hashCode(this.username);
        hash = 79 * hash + Objects.hashCode(this.password);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.avatar);
        hash = 79 * hash + Objects.hashCode(this.services);
        hash = 79 * hash + Objects.hashCode(this.reviews);
        hash = 79 * hash + Objects.hashCode(this.roles);
        hash = 79 * hash + Objects.hashCode(this.photos);
        hash = 79 * hash + Objects.hashCode(this.avgRate);
        return hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserModel> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<UserModel> subscribers) {
        this.subscribers = subscribers;
    }

    public List<LikeModel> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeModel> likes) {
        this.likes = likes;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

}
