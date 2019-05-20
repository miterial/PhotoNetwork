package com.course.PhotoNetwork.controller.web;

import com.course.PhotoNetwork.service.UserService;
import com.course.PhotoNetwork.controller.rest.AdminController;
import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.PhotoDtoIn;
import com.course.PhotoNetwork.model.dto.UserDtoIn;
import com.course.PhotoNetwork.repository.LikeRepository;
import com.course.PhotoNetwork.service.CategoryService;
import com.course.PhotoNetwork.service.PhotoService;
import com.course.PhotoNetwork.service.ServicesService;
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
    @Autowired
    private PhotoService photoService;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LikeRepository likeRepository;

    @GetMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect("/");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage(ModelAndView modelAndView, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect("/");
        } else {
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

    @GetMapping("/photo/{photoId}")
    public ModelAndView getPhoto(@PathVariable Long photoId, ModelAndView model) {
        model.setViewName("photo");
        try {
            PhotoModel photo = photoService.updateViewCount(photoId);

            model.addObject("photo", photoService.toDto(photo));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel user = userService.findByEmail(auth.getName());
            model.addObject("liked", likeRepository.findByPhotoAndUser(photo, user) != null);

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    /** Show new photos on main page */
    //todo: show photos of users to which this user is subscribed
    @GetMapping({"", "/"})
    public ModelAndView home(ModelAndView model) {
        model.setViewName("index");

        try {
            model.addObject("photos", photoService.toDto(photoService.getNewPhotos()));

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    /** Show popular photos */
    @GetMapping("/popular")
    public ModelAndView popular(ModelAndView model) {
        model.setViewName("index");

        try {
            model.addObject("photos", photoService.toDto(photoService.getPopularPhotos()));

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    @GetMapping("/account")
    public ModelAndView account(ModelAndView model) {
        model.setViewName("account_form");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel user = userService.findByEmail(auth.getName());
            model.addObject("currentUser", userService.toDto(user));

            if(user.isProvideServices())
                model.addObject("services", servicesService.getDefaultServices());

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    @GetMapping("/upload")
    public ModelAndView upload(ModelAndView model) {
        model.setViewName("upload");
        try {
            model.addObject("newPhoto", new PhotoDtoIn());

            model.addObject("categories", categoryService.findAll());

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    @GetMapping("/user/{userId}")
    public ModelAndView user(@PathVariable Long userId, ModelAndView model) {
        model.setViewName("account");
        try {
            model.addObject("user", userService.toDto(userService.findById(userId).get()));

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            model.addObject("isCurrent", userService.findByEmail(auth.getName()).getId() != userId);

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }
}
