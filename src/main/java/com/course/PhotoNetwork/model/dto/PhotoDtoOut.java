package com.course.PhotoNetwork.model.dto;

import com.course.PhotoNetwork.model.types.PhotoLicense;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private String uploaddate;
    @JsonProperty
    private UserDtoOut user;
    @JsonProperty
    private String category;
    @JsonProperty
    private String photofile;
    @JsonProperty
    private PhotoLicense license;

    public PhotoDtoOut() {}

    public PhotoDtoOut(String id, String name, String description, long likecount, long viewcount, Date uploaddate, UserDtoOut user, String category, String photofile, PhotoLicense license) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.likecount = likecount;
        this.viewcount = viewcount;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        this.uploaddate = df.format(uploaddate);
        this.user = user;
        this.category = category;
        this.photofile = photofile;
        this.license = license;
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

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
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

    public PhotoLicense getLicense() {
        return license;
    }

    public void setLicense(PhotoLicense license) {
        this.license = license;
    }
}
