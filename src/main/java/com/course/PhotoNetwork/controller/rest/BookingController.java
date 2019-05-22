package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.dto.BookingServiceDtoIn;
import com.course.PhotoNetwork.model.dto.BookingUserInfoDtoIn;
import com.course.PhotoNetwork.model.dto.UserDtoIn;
import com.course.PhotoNetwork.model.dto.UserServicesDtoIn;
import com.course.PhotoNetwork.service.BookingService;
import com.course.PhotoNetwork.service.ServicesService;
import com.course.PhotoNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private UserService userService;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity bookService(@RequestBody BookingServiceDtoIn bookingServiceDtoIn) {
        try {
            bookingService.bookService(bookingServiceDtoIn);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/status")
    public String changeStatus(@RequestBody BookingUserInfoDtoIn dtoIn) {
        try {
            bookingService.changeStatus(dtoIn);
            return "redirect:/services";
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/error";
        }
    }

    @PostMapping("/remove/{bookingId}")
    public ResponseEntity removeBooking(@PathVariable Long bookingId, HttpServletResponse response, Authentication auth) throws IOException {
        try {
            bookingService.removeBooking(bookingId, auth);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
