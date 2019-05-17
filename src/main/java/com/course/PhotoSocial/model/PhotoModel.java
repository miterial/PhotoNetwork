package com.course.PhotoSocial.model;


import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "photo")
public class PhotoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long photo_id;

    private String photo_name;

    private String photo_description;

    private long photo_likes;

    private long photo_views;

    private Date upload_data = Calendar.getInstance().getTime();

    @Lob
    @Column(nullable = false)
    private String photo_file;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private UserModel user;

    @ManyToOne
    private CategoryModel category;

    public PhotoModel() {
    }

    public void setPhoto_id(long photo_id) {
        this.photo_id = photo_id;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public void setPhoto_description(String photo_description) {
        this.photo_description = photo_description;
    }

    public void setPhoto_likes(long photo_likes) {
        this.photo_likes = photo_likes;
    }

    public void setPhoto_views(long photo_views) {
        this.photo_views = photo_views;
    }

    public void setUpload_data(Date upload_data) {
        this.upload_data = upload_data;
    }

    public void setPhoto_file(String photo_file) {
        this.photo_file = photo_file;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public long getPhoto_id() {
        return photo_id;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public String getPhoto_description() {
        return photo_description;
    }

    public long getPhoto_likes() {
        return photo_likes;
    }

    public long getPhoto_views() {
        return photo_views;
    }

    public Date getUpload_data() {
        return upload_data;
    }

    public String getPhoto_file() {
        return photo_file;
    }

    public UserModel getUser() {
        return user;
    }

    public CategoryModel getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.photo_id ^ (this.photo_id >>> 32));
        hash = 59 * hash + Objects.hashCode(this.photo_name);
        hash = 59 * hash + Objects.hashCode(this.photo_description);
        hash = 59 * hash + (int) (this.photo_likes ^ (this.photo_likes >>> 32));
        hash = 59 * hash + (int) (this.photo_views ^ (this.photo_views >>> 32));
        hash = 59 * hash + Objects.hashCode(this.upload_data);
        hash = 59 * hash + Objects.hashCode(this.photo_file);
        hash = 59 * hash + Objects.hashCode(this.user);
        hash = 59 * hash + Objects.hashCode(this.category);
        return hash;
    }
}
