package com.course.PhotoSocial.service;

import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory(CategoryModel category) {
        //todo
    }

    public List<CategoryModel> getAllCategories() {
        return null; //todo
    }

    public Optional<CategoryModel> findByName(String category) {
        return categoryRepository.findByName(category);
    }

    public Optional<CategoryModel> findById(long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public void save(CategoryModel category) {
        categoryRepository.save(category);
    }

    public void deleteByName(String categoryName) {
        categoryRepository.deleteByName(categoryName);
    }
}
