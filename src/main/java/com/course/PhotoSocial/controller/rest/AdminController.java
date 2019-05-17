package com.course.PhotoSocial.controller.rest;

import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.model.RoleModel;
import com.course.PhotoSocial.service.CategoryService;
import com.course.PhotoSocial.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    RoleService roleService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/role")
    public HttpStatus addRole(@RequestParam String roleName) {
        RoleModel role = new RoleModel();

        role.setRolename(roleName);

        try {
            roleService.addRole(role);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return HttpStatus.OK;
    }

    @DeleteMapping("/role")
    public HttpStatus deleteRole(@RequestParam String roleName) {
        try {
            roleService.deleteRole(roleName);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return HttpStatus.OK;
    }

    @PostMapping("/category")
    public HttpStatus addCategory(@RequestParam String categoryName) {
        CategoryModel category = new CategoryModel();

        category.setName(categoryName);

        try {
            categoryService.save(category);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return HttpStatus.OK;
    }

    @PutMapping("/category")
    public HttpStatus updateCategory(@RequestParam String categoryName) {
        Optional<CategoryModel> category = categoryService.findByName(categoryName);

        if(category.isPresent()) {
            category.get().setName(categoryName);

            try {
                categoryService.save(category.get());
            } catch (Exception ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

            }

            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @DeleteMapping("/category")
    public HttpStatus deleteCategory(@RequestParam String categoryName) {
        try {
            categoryService.deleteByName(categoryName);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return HttpStatus.OK;
    }

}
