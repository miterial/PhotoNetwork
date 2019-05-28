package com.course.PhotoNetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CategoryDto {

    @JsonProperty
    private Long id;
    @JsonProperty
    @NotBlank(message = "Название категории не может быть пустым")
    private String name;

    public CategoryDto() {
    }

    public CategoryDto(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
