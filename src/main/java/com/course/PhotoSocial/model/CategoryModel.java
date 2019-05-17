package com.course.PhotoSocial.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long category_id;

    @Column(nullable = false, unique = true)
    private String category_name;

    @OneToMany(mappedBy = "category")
    private List<PhotoModel> photos;

    public CategoryModel() {
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.category_id);
        hash = 37 * hash + Objects.hashCode(this.category_name);
        hash = 37 * hash + Objects.hashCode(this.photos);
        return hash;
    }
}
