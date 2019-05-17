package com.course.PhotoSocial.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PhotoDtoOut {

    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private long likecount;
    @JsonProperty
    private long viewcount;
    @JsonProperty
    private Date uploaddate;
    @JsonProperty
    private UserDtoOut user;
    @JsonProperty
    private String category;
    @JsonProperty
    private String photofile;

    public PhotoDtoOut() {}

    public PhotoDtoOut(String id, String name, String description, long likecount, long viewcount, Date uploaddate, UserDtoOut user, String category, String photofile) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.likecount = likecount;
        this.viewcount = viewcount;
        this.uploaddate = uploaddate;
        this.user = user;
        this.category = category;
        this.photofile = photofile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLikecount() {
        return likecount;
    }

    public void setLikecount(long likecount) {
        this.likecount = likecount;
    }

    public long getViewcount() {
        return viewcount;
    }

    public void setViewcount(long viewcount) {
        this.viewcount = viewcount;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    public UserDtoOut getUser() {
        return user;
    }

    public void setUser(UserDtoOut user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotofile() {
        return photofile;
    }

    public void setPhotofile(String photofile) {
        this.photofile = photofile;
    }

    public String getId() {
        return id;
    }
}
