package com.course.PhotoSocial.repository;

import com.course.PhotoSocial.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    void deleteByName(String categoryName);

    Optional<CategoryModel> findByName(String category);
}
