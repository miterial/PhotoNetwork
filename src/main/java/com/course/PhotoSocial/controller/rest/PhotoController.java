package com.course.PhotoSocial.controller.rest;

import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.model.PhotoModel;
import com.course.PhotoSocial.model.RoleModel;
import com.course.PhotoSocial.model.UserModel;
import com.course.PhotoSocial.model.dto.PhotoDtoIn;
import com.course.PhotoSocial.model.dto.PhotoDtoOut;
import com.course.PhotoSocial.model.dto.RoleDtoOut;
import com.course.PhotoSocial.model.dto.UserDtoOut;
import com.course.PhotoSocial.service.CategoryService;
import com.course.PhotoSocial.service.PhotoService;
import com.course.PhotoSocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public HttpStatus addPhoto(@ModelAttribute PhotoDtoIn newPhoto) {

        PhotoModel photo = null;

        try {
            photo = new PhotoModel();
            photo.setName(newPhoto.getName());
            photo.setDescription(newPhoto.getDescription());
            photo.setPhotofile("data:image/jpeg;base64," + Base64.getEncoder().
                    encodeToString(newPhoto.getPhotofile().getBytes()));
            long catId = Long.parseLong(newPhoto.getCategory());
            photo.setCategory(categoryService.findById(catId).get());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel user = userService.findByEmail(auth.getName());
            photo.setUser(user);

            photoService.save(photo);

            return HttpStatus.OK;

        } catch (IOException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "/modify")
    public long modifyPhoto(@RequestParam String photo_name,
                            @RequestParam String photo_descrp,
                            @RequestParam String category,
                            @RequestParam long photo_id) {

        PhotoModel photo = null;

        try {
            Optional<PhotoModel> optional = photoService.findById(photo_id);
            if (optional.isPresent()) {
                photo = optional.get();
                photo.setName(photo_name);
                photo.setDescription(photo_descrp);
                photo.setCategory(categoryService.findByName(category).get());
                photoService.save(photo);
            }
        } catch (IllegalStateException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return photo.getId();
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

    @RequestMapping("/category/{categoryId}")
    public List<PhotoDtoOut> getPhotosFromCategory(@PathVariable String categoryId) {
        List<PhotoModel> photosInCategory = null;
        ArrayList<PhotoDtoOut> photosInCategoryDTO = new ArrayList<>();

        try {
            long catId = Long.parseLong(categoryId);
            Optional<CategoryModel> category = categoryService.findById(catId);
            if(category.isPresent())
            photosInCategory = photoService.findByCategory(category.get());
            photosInCategoryDTO.addAll(photoService.toDto(photosInCategory));
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return photosInCategoryDTO;
    }
}