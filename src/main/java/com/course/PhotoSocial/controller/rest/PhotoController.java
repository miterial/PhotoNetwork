package com.course.PhotoSocial.controller.rest;

import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.model.PhotoModel;
import com.course.PhotoSocial.model.RoleModel;
import com.course.PhotoSocial.model.UserModel;
import com.course.PhotoSocial.model.dto.PhotoDtoOut;
import com.course.PhotoSocial.model.dto.RoleDtoOut;
import com.course.PhotoSocial.model.dto.UserDtoOut;
import com.course.PhotoSocial.service.CategoryService;
import com.course.PhotoSocial.service.PhotoService;
import com.course.PhotoSocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Controller
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/add")
    public long addPhoto(@RequestParam MultipartFile photo_file,
                         @RequestParam String photo_name,
                         @RequestParam String photo_descrp,
                         @RequestParam String uname,
                         @RequestParam String category) {

        PhotoModel photo = null;

        try {
            photo = new PhotoModel();
            photo.setName(photo_name);
            photo.setDescription(photo_descrp);
            photo.setPhotofile("data:image/jpeg;base64," + Base64.getEncoder().
                    encodeToString(photo_file.getBytes()));
            photo.setCategory(categoryService.findByName(category));
            photo.setUser(userService.findByUsername(uname));
            photoService.save(photo);
        } catch (IOException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return photo.getId();
    }

    @RequestMapping(value = "modifyPhoto")
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
                photo.setCategory(categoryService.findByName(category));
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
            res.addAll(entityToDto(popular_photos));
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
            res.addAll(entityToDto(newPhotos));
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
            photosInCategoryDTO.addAll(entityToDto(photosInCategory));
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return photosInCategoryDTO;
    }

    private List<PhotoDtoOut> entityToDto(List<PhotoModel> enteties) {
        List<PhotoDtoOut> res = new ArrayList<>();
        RoleDtoOut roleDTO;
        UserDtoOut userDTO;
        PhotoDtoOut photoDTO;

        try {
            for (PhotoModel photo : enteties) {
                UserModel usr = photo.getUser();
                ArrayList<RoleDtoOut> roles = new ArrayList<>();
                for (RoleModel r : usr.getRoles()) {
                    roleDTO = new RoleDtoOut(r.getRolename());
                    roles.add(roleDTO);
                }
                userDTO = new UserDtoOut(usr.getName(), usr.getSurname(),
                        usr.getBirthday(), usr.getRegdate(),
                        usr.getUsername(), usr.getEmail(),
                        usr.getAvatar(), roles);

                photoDTO = new PhotoDtoOut(photo.getName(),
                        photo.getDescription(),
                        photo.getLikecount(),
                        photo.getViewcount(),
                        photo.getUploaddate(),
                        userDTO,
                        photo.getCategory().getName(),
                        photo.getPhotofile());

                res.add(photoDTO);
            }
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}