package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    void deleteByName(String categoryName);

    Optional<CategoryModel> findByName(String category);
}
