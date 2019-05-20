package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PhotoDtoOutSmall {

    @JsonProperty
    private String id;
    @JsonProperty
    private Date uploaddate;
    @JsonProperty
    private String photofile;

    public PhotoDtoOutSmall() {}

    public PhotoDtoOutSmall(String id, Date uploaddate, String photofile) {
        this.id = id;
        this.uploaddate = uploaddate;
        this.photofile = photofile;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
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
