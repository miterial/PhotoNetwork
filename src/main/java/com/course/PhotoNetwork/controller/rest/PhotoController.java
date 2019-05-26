package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.PhotoDtoOutSmall;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String addPhoto(@ModelAttribute PhotoDtoIn newPhoto, ModelAndView model) {

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

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel user = userService.findByEmail(auth.getName());
            photo.setUser(user);

            photoService.save(photo);

            return "redirect:/upload";
        } catch (IOException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/modify", consumes = "multipart/form-data")
    public String modifyPhoto(@ModelAttribute PhotoDtoIn photoDtoIn, ModelAndView model) {

        PhotoModel photo;

        try {
            Optional<PhotoModel> optional = photoService.findById(Long.parseLong(photoDtoIn.getId()));
            if (optional.isPresent()) {
                photo = optional.get();
                photo.setName(photoDtoIn.getName());
                photo.setDescription(photoDtoIn.getDescription());
                photo.setCategory(categoryService.findByName(photoDtoIn.getCategory()).get());
                photoService.save(photo);

                return "redirect:/upload";
            }
        } catch (IllegalStateException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/error";
    }

    @DeleteMapping
    public HttpStatus deletePhoto(long photo_id) {
        try {
            photoService.deleteById(photo_id);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HttpStatus.OK;
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