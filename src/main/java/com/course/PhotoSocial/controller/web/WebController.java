package com.course.PhotoSocial.controller.web;

import com.course.PhotoSocial.controller.rest.AdminController;
import com.course.PhotoSocial.model.UserModel;
import com.course.PhotoSocial.model.dto.UserDtoIn;
import com.course.PhotoSocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect("/");
        }
        else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage(ModelAndView modelAndView, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect("/");
        }
        else {
            modelAndView.addObject("newUser", new UserDtoIn());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void registration(UserDtoIn newUser, HttpServletResponse response) throws IOException {

        try {
            userService.registerUser(newUser);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/");
    }
}
