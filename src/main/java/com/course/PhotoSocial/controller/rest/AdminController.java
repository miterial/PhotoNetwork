package com.course.PhotoSocial.controller.rest;

import com.course.PhotoSocial.config.security.RoleModel;
import com.course.PhotoSocial.config.security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    RoleService roleService;

    @PostMapping("/role")
    public HttpStatus addRole(@RequestParam String roleName) {
        RoleModel role = new RoleModel();

        role.setRole_name(roleName);

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

}
