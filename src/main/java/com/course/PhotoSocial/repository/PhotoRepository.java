package com.course.PhotoSocial.repository;

import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.model.PhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoModel, Long> {
    List<PhotoModel> findByCategory(CategoryModel category);
}
