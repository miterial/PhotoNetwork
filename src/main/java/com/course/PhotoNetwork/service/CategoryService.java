package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.dto.CategoryDto;
import com.course.PhotoNetwork.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PhotoService photoService;

    public Optional<CategoryModel> findByName(String category) {
        return categoryRepository.findByName(category);
    }

    public Optional<CategoryModel> findById(long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public void save(CategoryModel category) {
        categoryRepository.save(category);
    }

    public void delete(String categoryName) {
        CategoryModel category = categoryRepository.findByName(categoryName).orElseThrow(()->new EntityNotFoundException("Категория с таким именем не найдена"));

        CategoryModel defaultCategory = categoryRepository.findByName("default").orElseThrow(()->new EntityNotFoundException("Отсутствует дефолтная категория!"));

        List<PhotoModel> photos = photoService.findByCategory(category);
        for(PhotoModel photo : photos) {
            photo.setCategory(defaultCategory);
        }
        photoService.saveAll(photos);

        categoryRepository.delete(category);
    }

    public List<CategoryModel> findAll() {
        return categoryRepository.findAll();
    }

    public List<CategoryDto> toDto(List<CategoryModel> all) {
        List<CategoryDto> res = new ArrayList<>();
        for(CategoryModel cat : all) {
            res.add(new CategoryDto(cat.getName(),cat.getId()));
        }
        return res;
    }

    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
