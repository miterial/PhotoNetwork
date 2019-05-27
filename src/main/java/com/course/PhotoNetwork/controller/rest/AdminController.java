package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.exceptions.ServiceIsInUseException;
import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.dto.CategoryDto;
import com.course.PhotoNetwork.model.dto.ServiceDto;
import com.course.PhotoNetwork.service.CategoryService;
import com.course.PhotoNetwork.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity addCategory(@RequestBody CategoryDto dtoIn) {
        CategoryModel category = new CategoryModel();

        category.setName(dtoIn.getName());

        try {
            categoryService.save(category);
            return ResponseEntity.status(HttpStatus.OK).body("Категория добавлена");
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
        }
    }

    @PutMapping("/category")
    public ResponseEntity updateCategory(@RequestBody CategoryDto dtoIn) {
        Optional<CategoryModel> category = categoryService.findById(dtoIn.getId());

        if(category.isPresent()) {
            category.get().setName(dtoIn.getName());

            try {
                categoryService.save(category.get());
                return ResponseEntity.status(HttpStatus.OK).body("Категория обновлена");
            } catch (Exception ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Категория не найдена");
    }

    /**
     * Delete category by name; 'default' category cannot be deleted
     */
    @DeleteMapping("/category")
    public ResponseEntity deleteCategory(@RequestBody CategoryDto dto) {
        try {
            categoryService.deleteByName(dto.getName());
            return ResponseEntity.status(HttpStatus.OK).body("категория удалена");
        } catch (EntityNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не удалось удалить категорию");
        }
    }


    @PostMapping("/service")
    public ResponseEntity addService(@RequestBody ServiceDto dto) {
        ServiceModel service = new ServiceModel();

        service.setName(dto.getName());

        try {
            servicesService.save(service);
            return ResponseEntity.status(HttpStatus.OK).body("Услуга добавлена");
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
        }

    }

    @PutMapping("/service")
    public ResponseEntity updateService(@RequestBody ServiceDto dtoIn) {
        Optional<ServiceModel> service = servicesService.findById(dtoIn.getId());

        if(service.isPresent()) {
            service.get().setName(dtoIn.getName());

            try {
                servicesService.save(service.get());
                return ResponseEntity.status(HttpStatus.OK).body("Услуга обновлена");
            } catch (Exception ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Услуга не найдена");
    }

    @DeleteMapping("/service")
    public ResponseEntity deleteService(@RequestBody ServiceDto dto) {
        try {
            servicesService.deleteByName(dto.getName());
            return ResponseEntity.status(HttpStatus.OK).body("Услуга удалена");
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
        }
    }
}
