package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.dto.BookingServiceDtoIn;
import com.course.PhotoNetwork.model.dto.UserDtoIn;
import com.course.PhotoNetwork.model.dto.UserServicesDtoIn;
import com.course.PhotoNetwork.service.BookingService;
import com.course.PhotoNetwork.service.ServicesService;
import com.course.PhotoNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/account")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private BookingService bookingService;

    @PostMapping("/update")
    public void updateAccount(@ModelAttribute UserDtoIn newUser, HttpServletResponse response) throws IOException {
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
            servicesService.changeServices(services);
            return HttpStatus.OK;
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/subscribe/{userId}")
    public HttpStatus subscribe(@PathVariable Long userId) {
        try {
            userService.subscribeToUser(userId);
            return HttpStatus.OK;
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/booking")
    public HttpStatus bookService(@RequestBody BookingServiceDtoIn bookingServiceDtoIn) {
        try {
            bookingService.bookService(bookingServiceDtoIn);
            return HttpStatus.OK;
        } catch (IllegalStateException e) {
            return HttpStatus.CONFLICT;
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
