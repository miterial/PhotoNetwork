package com.course.PhotoNetwork.controller.web;

import com.course.PhotoNetwork.controller.rest.PhotoController;
import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.dto.*;
import com.course.PhotoNetwork.service.*;
import com.course.PhotoNetwork.controller.rest.AdminController;
import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.repository.LikeRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private BookingService bookingService;

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
            model.addObject("isCurrentUser", photo.getUser() == user);

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    /** Show new photos on main page */
    @GetMapping({"", "/"})
    public ModelAndView home(ModelAndView model) {
        model.setViewName("index");

        try {
            model.addObject("photos", photoService.toDto(photoService.getNewPhotos()));
            model.addObject("title", "Новые фото от интересных вам пользователей");
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
            model.addObject("isCurrent", userService.findByEmail(auth.getName()).getId() == userId);

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

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
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
            model.setViewName("error");
        }
        return model;
    }

    @GetMapping("/services")
    public ModelAndView getSchedule(ModelAndView model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel user = userService.findByEmail(auth.getName());
            List<BookingServiceDtoOut> booked = bookingService.toDto(bookingService.findUserBookedServices(user));
            List<BookingServiceDtoOut> upcoming = bookingService.toDto(bookingService.findUserUpcomingServices(user));
            BookingUserInfoDtoOut schedule = new BookingUserInfoDtoOut(user.getId(),booked,upcoming);
            model.addObject("schedule",schedule);
            model.setViewName("services");
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
            model.setViewName("error");
        }
        return model;
    }
}
