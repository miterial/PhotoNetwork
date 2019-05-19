package com.course.PhotoNetwork.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String surname;

    private Date birthday;

    private Date regdate = Calendar.getInstance().getTime();

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    @Size(min=6)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Lob
    @Column(length = 3000)
    private String avatar;

    private String description;

    private boolean enabled = true;

    private boolean provideServices = true;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<RoleModel> roles;

    @OneToMany(mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<ServiceModel> services;

    @OneToMany(mappedBy = "user")
    private List<PhotoModel> photos;

    @OneToMany(mappedBy = "user")
    private List<LikeModel> likes;

    public void setId(long id) {
        this.id = id;
    }

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }


    public List<ServiceModel> getServices() {
        return services;
    }

    public void setServices(List<ServiceModel> services) {
        this.services = services;
    }

    public boolean isProvideServices() {
        return provideServices;
    }

    public void setProvideServices(boolean provideServices) {
        this.provideServices = provideServices;
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
        hash = 79 * hash + (this.enabled ? 1 : 0);
        hash = 79 * hash + (this.provideServices ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.services);
        hash = 79 * hash + Objects.hashCode(this.roles);
        hash = 79 * hash + Objects.hashCode(this.photos);
        return hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}