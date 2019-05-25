package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.service.CategoryService;
import com.course.PhotoNetwork.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ServicesService servicesService;

    @PostMapping("/category")
    public ResponseEntity addCategory(@RequestParam String categoryName) {
        CategoryModel category = new CategoryModel();

        category.setName(categoryName);

        try {
            categoryService.save(category);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/category")
    public ResponseEntity updateCategory(@RequestParam String categoryName) {
        Optional<CategoryModel> category = categoryService.findByName(categoryName);

        if(category.isPresent()) {
            category.get().setName(categoryName);

            try {
                categoryService.save(category.get());
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/category")
    public ResponseEntity deleteCategory(@RequestParam String categoryName) {
        try {
            categoryService.deleteByName(categoryName);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/service")
    public ResponseEntity addService(@RequestParam String serviceName) {
        ServiceModel service = new ServiceModel();

        service.setName(serviceName);

        try {
            servicesService.save(service);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/service")
    public ResponseEntity updateService(@RequestParam String serviceName) {
        Optional<ServiceModel> service = servicesService.findByName(serviceName);

        if(service.isPresent()) {
            service.get().setName(serviceName);

            try {
                servicesService.save(service.get());
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/service")
    public ResponseEntity deleteService(@RequestParam String serviceName) {
        try {
            servicesService.deleteByName(serviceName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
