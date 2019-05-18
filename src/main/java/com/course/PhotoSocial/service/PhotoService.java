package com.course.PhotoSocial.service;

import com.course.PhotoSocial.controller.rest.PhotoController;
import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.model.PhotoModel;
import com.course.PhotoSocial.model.RoleModel;
import com.course.PhotoSocial.model.UserModel;
import com.course.PhotoSocial.model.dto.PhotoDtoOut;
import com.course.PhotoSocial.model.dto.RoleDtoOut;
import com.course.PhotoSocial.model.dto.UserDtoOut;
import com.course.PhotoSocial.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserService userService;

    public void save(PhotoModel photo) {
        photoRepository.save(photo);
    }

    public Optional<PhotoModel> findById(long photo_id) {
        return photoRepository.findById(photo_id);
    }

    public void deleteById(long photo_id) {
        photoRepository.deleteById(photo_id);
    }

    public List<PhotoModel> getPopularPhotos() {
        //todo
        return new ArrayList<>();
    }

    public List<PhotoModel> getNewPhotos() {
        //todo
        return new ArrayList<>();
    }

    public List<PhotoModel> findByCategory(CategoryModel category) {
        return photoRepository.findByCategory(category);
    }

    public List<PhotoDtoOut> toDto(PhotoModel photo) {
        List<PhotoDtoOut> res = new ArrayList<>();
        RoleDtoOut roleDTO;
        UserDtoOut userDTO;
        PhotoDtoOut photoDTO;

        try {
                userDTO = userService.toDto(photo.getUser());

                photoDTO = new PhotoDtoOut(String.valueOf(photo.getId()), photo.getName(),
                        photo.getDescription(),
                        photo.getLikecount(),
                        photo.getViewcount(),
                        photo.getUploaddate(),
                        userDTO,
                        photo.getCategory().getName(),
                        photo.getPhotofile());

                res.add(photoDTO);
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public List<PhotoDtoOut> toDto(List<PhotoModel> entities) {
        List<PhotoDtoOut> res = new ArrayList<>();
        UserDtoOut userDTO;
        PhotoDtoOut photoDTO;

        try {
            for (PhotoModel photo : entities) {
                userDTO = userService.toDto(photo.getUser());

                photoDTO = new PhotoDtoOut(String.valueOf(photo.getId()), photo.getName(),
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
