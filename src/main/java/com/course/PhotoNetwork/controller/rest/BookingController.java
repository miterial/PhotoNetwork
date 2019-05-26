package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.BookingServiceDtoIn;
import com.course.PhotoNetwork.model.dto.BookingUserInfoDtoIn;
import com.course.PhotoNetwork.service.BookingService;
import com.course.PhotoNetwork.service.ServicesService;
import com.course.PhotoNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity bookService(@RequestBody BookingServiceDtoIn bookingServiceDtoIn, Authentication auth) {
        try {
            UserModel currentUser = userService.findByEmail(auth.getName());
            BookingModel booking = bookingService.bookService(currentUser, bookingServiceDtoIn);
            return ResponseEntity.ok(bookingService.toDto(booking));
        } catch (IllegalStateException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/status")
    public ResponseEntity changeStatus(@RequestBody BookingUserInfoDtoIn dtoIn) {
        try {
            BookingModel booking = bookingService.changeStatus(dtoIn);
            return ResponseEntity.ok(bookingService.toDto(booking));
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove/{bookingId}")
    public ResponseEntity removeBooking(@PathVariable Long bookingId, Authentication auth) throws IOException {
        try {
            bookingService.removeBooking(bookingId, auth);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity deleteService(@PathVariable Long bookingId, Authentication auth) {
        try {
            UserModel curUser = userService.findByEmail(auth.getName());
            if(!userService.isAdmin(curUser))
                throw new IllegalArgumentException("Для данного действия нужны права администратора");
            bookingService.deleteById(bookingId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
