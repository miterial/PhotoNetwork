package com.course.PhotoNetwork.controller.web;

import com.course.PhotoNetwork.controller.rest.PhotoController;
import com.course.PhotoNetwork.controller.rest.UserController;
import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.dto.*;
import com.course.PhotoNetwork.model.types.BookingEnum;
import com.course.PhotoNetwork.service.*;
import com.course.PhotoNetwork.controller.rest.AdminController;
import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.repository.LikeRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.print.Book;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/login")
    public ModelAndView loginPage(ModelAndView modelAndView, HttpServletResponse response, Authentication auth) throws IOException {
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect("/");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage(ModelAndView modelAndView, HttpServletResponse response, Authentication auth) throws IOException {
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect("/");
        } else {
            modelAndView.addObject("newUser", new UserDtoIn());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
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
            model.addObject("isAuthor", photo.getUser() == user);
            model.addObject("isAdmin", userService.isAdmin(user));

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    /**
     * Show new photos on main page
     */
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

    /**
     * Show popular photos
     */
    @GetMapping("/popular")
    public ModelAndView popular(ModelAndView model) {
        model.setViewName("index");

        try {
            model.addObject("photos", photoService.toDto(photoService.getPopularPhotos()));
            model.addObject("title", "Популярное");

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    @GetMapping("/account")
    public ModelAndView account(ModelAndView model, Authentication auth) {
        model.setViewName("account_form");
        try {
            UserModel user = userService.findByEmail(auth.getName());

            model.addObject("currentUser", userService.toDto(user));

            Set<ServiceDto> services = new HashSet<>();
            if (userService.isMaster(user)) {
                services = servicesService.toDto(servicesService.findByMaster(user));// + services that user provides
                services.addAll(servicesService.getDefaultServices());   // +default services
            }
            model.addObject("services", services);

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    @GetMapping("/user/{userId}")
    public ModelAndView user(@PathVariable Long userId, ModelAndView model, Authentication auth) {
        model.setViewName("account");
        try {
            UserModel user = userService.findById(userId).orElseThrow(IllegalArgumentException::new);
            model.addObject("user", userService.toDto(user));
            if (userService.isMaster(user)) {
                model.addObject("services", servicesService.getDefaultServices());
                model.addObject("reviews", reviewService.toDto(reviewService.findAll()));
            }

            if (auth != null) {
                UserModel currentUser = userService.findByEmail(auth.getName());
                model.addObject("subscribed", user.getSubscribers().contains(currentUser));
                model.addObject("isCurrent", currentUser.getId() == userId);

                List<BookingModel> currentUserBookings = bookingService.findByClientAndStatus(currentUser, BookingEnum.FINISHED);
                model.addObject("currentUserBookings", bookingService.toDto(currentUserBookings));
                if (!currentUserBookings.isEmpty() && userId != currentUser.getId()) {
                    model.addObject("newReview", new ReviewDtoIn());
                    model.addObject("curId", currentUser.getId());
                }
            }
            model.addObject("canWriteReview", false);

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
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
            BookingUserInfoDtoOut schedule = new BookingUserInfoDtoOut(user.getId(), booked, upcoming);
            model.addObject("schedule", schedule);
            model.setViewName("services");
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
            model.setViewName("error");
            model.addObject("errorMessage", "Не удалось загрузить страницу");
        }
        return model;
    }

    @GetMapping("/upload")
    public ModelAndView upload(@RequestParam(required = false) Long modify, ModelAndView model, Authentication auth) {
        model.setViewName("upload");
        try {
            if (modify == null)
                model.addObject("newPhoto", new PhotoDtoIn());
            else
                model.addObject("newPhoto", photoService.toDto(photoService.findById(modify).get()));

            model.addObject("categories", categoryService.findAll());
            model.addObject("userPhotos", photoService.findByUser(auth.getName()));

        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            model.setViewName("error");
            model.addObject("errorMessage", "Не получается загрузить страницу изменения фото");
        }
        return model;
    }

    @GetMapping("/administrator")
    public ModelAndView admin(ModelAndView model, Authentication auth) {
        try {
            UserModel currentUser = userService.findByEmail(auth.getName());
            if (userService.isAdmin(currentUser)) {
                model.setViewName("admin");
                model.addObject("categories", categoryService.toDto(categoryService.findAll()));
                model.addObject("services", servicesService.getDefaultServices());
            } else {
                model.setViewName("error");
                model.addObject("errorMessage", "Недостаточно прав для доступа к странице администратора");
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    /**
     * Form submission
     *
     * @param newUser
     */
    @PostMapping(value = "/registration")
    public String registrationForm(@Valid @ModelAttribute("newUser") UserDtoIn newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            //model.addAttribute("newUser", newUser);
            return "registration";
        }
        try {
            userService.registerUser(newUser);
            return "redirect:/";
        } catch (ConstraintViolationException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с такими email или login уже существует");
            return "redirect:/error";
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            redirectAttributes.addFlashAttribute("errorMessage", ex.getLocalizedMessage());
            return "redirect:/error";
        }
    }


    @GetMapping("/photographers")
    public String photographers(Model model, @RequestParam(name = "service", required = false) String service,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        model.addAttribute("title","Лучшие фотографы");

        try {
            Page<UserDtoOut> userPage = userService.findPaginated(service, PageRequest.of(currentPage - 1, pageSize));

            model.addAttribute("userPage", userPage);

            int totalPages = userPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMessage", ex.getLocalizedMessage());
            return "error";
        }

        return "photographers";
    }
}
