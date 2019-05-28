package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.service.CategoryService;
import com.course.PhotoNetwork.service.PhotoService;
import com.course.PhotoNetwork.service.UserService;
import com.course.PhotoNetwork.model.dto.PhotoDtoIn;
import com.course.PhotoNetwork.model.dto.PhotoDtoOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public String addPhoto(@Valid @ModelAttribute("newPhoto") PhotoDtoIn newPhoto, BindingResult bindingResult, Model model,Authentication auth) {
        UserModel user = userService.findByEmail(auth.getName());

        if(bindingResult.hasErrors()) {

            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("userPhotos", photoService.findByUser(user));
            return "upload";
        }

        PhotoModel photo = null;

        try {
            photo = new PhotoModel();
            photo.setName(newPhoto.getName());
            photo.setDescription(newPhoto.getDescription());
            photo.setPhotofile("data:image/jpeg;base64," + Base64.getEncoder().
                    encodeToString(newPhoto.getPhotofile().getBytes()));
            long catId = Long.parseLong(newPhoto.getCategory());
            photo.setCategory(categoryService.findById(catId).get());
            photo.setLicense(newPhoto.getLicense());

            photo.setUser(user);

            long id = photoService.save(photo);

            return "redirect:/photo/" + id;
        } catch (IOException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/error";
    }

    @PostMapping(value = "/modify", consumes = "multipart/form-data")
    public String modifyPhoto(@Valid @ModelAttribute PhotoDtoIn photoDtoIn, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            return "upload";
        }
        PhotoModel photo;

        try {
            Optional<PhotoModel> optional = photoService.findById(photoDtoIn.getId());
            if (optional.isPresent()) {
                photo = optional.get();
                photo.setName(photoDtoIn.getName());
                photo.setDescription(photoDtoIn.getDescription());
                photo.setCategory(categoryService.findByName(photoDtoIn.getCategory()).get());
                long id = photoService.save(photo);

                return "redirect:/photo/" + id;
            }
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Не удалось обновить фото");
        return "redirect:/error";
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity deletePhoto(@PathVariable Long photoId) {
        try {
            photoService.deleteById(photoId);
            return ResponseEntity.status(HttpStatus.OK).body("Фото удалено");
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
        }
    }

    @GetMapping(value = "/popular")
    public List<PhotoDtoOut> getPopularPhotos() {
        ArrayList<PhotoDtoOut> res = new ArrayList<>();

        try {
            List<PhotoModel> popular_photos = photoService.getPopularPhotos();
            res.addAll(photoService.toDto(popular_photos));
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    @GetMapping(value = "/new")
    public List<PhotoDtoOut> getNewPhots() {
        ArrayList<PhotoDtoOut> res = new ArrayList<>();

        try {
            List<PhotoModel> newPhotos = photoService.getNewPhotos();
            res.addAll(photoService.toDto(newPhotos));
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    @PostMapping("/like/{photoId}")
    public ResponseEntity likePhoto(@PathVariable Long photoId) {
        try {
            photoService.likePhoto(photoId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/dislike/{photoId}")
    public ResponseEntity dislikePhoto(@PathVariable Long photoId) {
        try {
            photoService.dislikePhoto(photoId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}