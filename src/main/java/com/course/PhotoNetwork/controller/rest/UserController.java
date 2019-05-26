package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.UserDtoIn;
import com.course.PhotoNetwork.model.dto.UserDtoOut;
import com.course.PhotoNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/account")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * API registration (for tests)
     * @param newUser
     * @return
     */
    @PostMapping(value = "/registration")
    public ResponseEntity registration(@RequestBody UserDtoIn newUser) {

        try {
            UserModel user = userService.registerUser(newUser);
            return ResponseEntity.ok(userService.toDto(user));
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update")
    public void updateAccount(@ModelAttribute UserDtoIn newUser, HttpServletResponse response) throws IOException {
        try {
            userService.updateUser(newUser);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/account");
    }

    @PostMapping("/subscribe/{userId}")
    public ResponseEntity subscribe(@PathVariable Long userId,Authentication auth) {
        try {
            UserModel currentUser = userService.findByEmail(auth.getName());
            userService.subscribeToUser(currentUser,userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unsubscribe/{userId}")
    public ResponseEntity unsubscribe(@PathVariable Long userId, Authentication auth) {
        try {
            UserModel currentUser = userService.findByEmail(auth.getName());
            userService.unsubscribeFromUser(currentUser,userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/avatar")
    @Deprecated
    public String uploadAvatar(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            userService.changeAvatar(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/account";
    }

    @GetMapping
    public UserDtoOut getUserByUsername(@PathParam(value = "email") String email) throws ParseException {
        return userService.toDto(userService.findByEmail(email));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId, Authentication auth) {
        try {
            UserModel curUser = userService.findByEmail(auth.getName());
            if(!userService.isAdmin(curUser))
                throw new IllegalArgumentException("Для данного действия нужны права администратора");
            userService.deleteById(userId);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
