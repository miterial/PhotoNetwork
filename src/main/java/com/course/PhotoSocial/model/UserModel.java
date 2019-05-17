package com.course.PhotoSocial.model;

import com.course.PhotoSocial.config.security.RoleModel;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;

    private String name;

    private String surname;

    private String country;

    private String language;

    private Date birthday;

    private Date reg_data = Calendar.getInstance().getTime();

    @Column(nullable = false, unique = true)
    private String user_name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String profile_picture;

    private boolean enabled = true;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<RoleModel> roles;

    @OneToMany(mappedBy = "user")
    private List<PhotoModel> photos;

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setReg_data(Date reg_data) {
        this.reg_data = reg_data;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
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

    public long getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getReg_data() {
        return reg_data;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile_picture() {
        return profile_picture;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.user_id ^ (this.user_id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.surname);
        hash = 79 * hash + Objects.hashCode(this.country);
        hash = 79 * hash + Objects.hashCode(this.language);
        hash = 79 * hash + Objects.hashCode(this.birthday);
        hash = 79 * hash + Objects.hashCode(this.reg_data);
        hash = 79 * hash + Objects.hashCode(this.user_name);
        hash = 79 * hash + Objects.hashCode(this.password);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.profile_picture);
        hash = 79 * hash + (this.enabled ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.roles);
        hash = 79 * hash + Objects.hashCode(this.photos);
        return hash;
    }
}
