package com.course.PhotoSocial.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class PhotoDtoIn {

    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty(required = true)
    private String category;
    @JsonProperty(required = true)
    private MultipartFile photofile;

    public PhotoDtoIn() {}

    public PhotoDtoIn(String id, String name, String description, String category, MultipartFile photofile) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getPhotofile() {
        return photofile;
    }

    public void setPhotofile(MultipartFile photofile) {
        this.photofile = photofile;
    }

    public String getId() {
        return id;
    }
}
