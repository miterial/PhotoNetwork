package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.PhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoModel, Long> {
    List<PhotoModel> findByCategory(CategoryModel category);
}
