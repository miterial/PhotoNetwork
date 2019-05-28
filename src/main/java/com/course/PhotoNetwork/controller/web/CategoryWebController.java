package com.course.PhotoNetwork.controller.web;

import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.dto.PhotoDtoOut;
import com.course.PhotoNetwork.service.CategoryService;
import com.course.PhotoNetwork.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class CategoryWebController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PhotoService photoService;

    @GetMapping("/category/{categoryName}")
    public ModelAndView getPhotosFromCategory(@PathVariable String categoryName, ModelAndView model) {
        List<PhotoModel> photosInCategory = null;
        ArrayList<PhotoDtoOut> photosInCategoryDTO = new ArrayList<>();

        try {
            Optional<CategoryModel> category = categoryService.findByName(categoryName);
            if(category.isPresent()) {
                photosInCategory = photoService.findByCategory(category.get());
                photosInCategoryDTO.addAll(photoService.toDto(photosInCategory));
            }
            model.addObject("photos",photosInCategoryDTO);
            model.addObject("title","Фото в категории " + categoryName);
            model.setViewName("index");
        } catch (Exception ex) {
            Logger.getLogger(CategoryWebController.class.getName()).log(Level.SEVERE, null, ex);
            model.setViewName("error");
        }
        return model;
    }



}
