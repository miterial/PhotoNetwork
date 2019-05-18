package com.course.PhotoSocial.controller.rest;

import com.course.PhotoSocial.model.dto.UserDtoIn;
import com.course.PhotoSocial.model.dto.UserServicesDtoIn;
import com.course.PhotoSocial.service.ServicesService;
import com.course.PhotoSocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/account")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ServicesService servicesService;

    @PostMapping("/update")
    public void updateAccount(UserDtoIn newUser, HttpServletResponse response) throws IOException {
        try {
            userService.updateUser(newUser);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/account");
    }

    @PostMapping("/services")
    @ResponseBody
    public HttpStatus changeServices(@RequestBody UserServicesDtoIn services) {
        try {
            System.out.println("saving services");
            servicesService.changeServices(services);
            return HttpStatus.OK;
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            userService.changeAvatar(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/account";
    }
}
