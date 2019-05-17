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
    private long id;

    private String name;

    private String description;

    private long likecount;

    private long viewcount;

    private Date uploaddate = Calendar.getInstance().getTime();

    @Lob
    @Column(nullable = false)
    private String photofile;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private UserModel user;

    @ManyToOne
    private CategoryModel category;

    public PhotoModel() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String photo_description) {
        this.description = photo_description;
    }

    public void setLikecount(long likecount) {
        this.likecount = likecount;
    }

    public void setViewcount(long viewcount) {
        this.viewcount = viewcount;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    public void setPhotofile(String photofile) {
        this.photofile = photofile;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getLikecount() {
        return likecount;
    }

    public long getViewcount() {
        return viewcount;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public String getPhotofile() {
        return photofile;
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
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + (int) (this.likecount ^ (this.likecount >>> 32));
        hash = 59 * hash + (int) (this.viewcount ^ (this.viewcount >>> 32));
        hash = 59 * hash + Objects.hashCode(this.uploaddate);
        hash = 59 * hash + Objects.hashCode(this.photofile);
        hash = 59 * hash + Objects.hashCode(this.user);
        hash = 59 * hash + Objects.hashCode(this.category);
        return hash;
    }
}
