package com.course.PhotoSocial.controller.rest;

import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryModel addCategory(@RequestParam String category_name){

        CategoryModel category = new CategoryModel();

        category.setCategory_name(category_name);

        try {
            categoryService.addCategory(category);
        } catch (Exception ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return category;
    }

    @GetMapping("/all")
    public List<CategoryModel> getAllCategories() {

        try {
            return categoryService.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

}
