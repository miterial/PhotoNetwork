package com.course.PhotoNetwork.model.dto;

import com.course.PhotoNetwork.model.types.PhotoLicense;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class PhotoDtoIn {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty(required = true)
    private String category;
    @JsonProperty(required = true)
    private MultipartFile photofile;
    @JsonProperty
    private PhotoLicense license;

    public PhotoDtoIn() {}

    public PhotoDtoIn(Long id, String name, String description, String category, MultipartFile photofile, PhotoLicense license) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public Long getId() {
        return id;
    }

    public PhotoLicense getLicense() {
        return license;
    }

    public void setLicense(PhotoLicense license) {
        this.license = license;
    }
}
