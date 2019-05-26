package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.controller.rest.PhotoController;
import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.LikeModel;
import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.PhotoDtoIn;
import com.course.PhotoNetwork.model.dto.PhotoDtoOut;
import com.course.PhotoNetwork.model.dto.PhotoDtoOutSmall;
import com.course.PhotoNetwork.model.dto.UserDtoOut;
import com.course.PhotoNetwork.repository.LikeRepository;
import com.course.PhotoNetwork.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeRepository likeRepository;

    public long save(PhotoModel photo) {
        return photoRepository.save(photo).getId();
    }

    public Optional<PhotoModel> findById(long photo_id) {
        return photoRepository.findById(photo_id);
    }

    public void deleteById(long photo_id) {
        photoRepository.deleteById(photo_id);
    }

    public List<PhotoModel> getPopularPhotos() {
        List<PhotoModel> res = photoRepository.findAll();
        Comparator<PhotoModel> comp = Comparator.comparing(PhotoModel::getLikecount).reversed();
        res.sort(comp);
        return res;
    }

    @Transactional
    public List<PhotoModel> getNewPhotos() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = userService.findByEmail(auth.getName());
        List<UserModel> subscribedTo = userService.findSubscribes(currentUser);
        List<PhotoModel> res = photoRepository.findAll().stream().filter(p -> subscribedTo.contains(p.getUser()) || p.getUser() == currentUser).sorted().collect(Collectors.toList());
        return res;
    }

    public List<PhotoModel> findByCategory(CategoryModel category) {
        return photoRepository.findByCategory(category);
    }

    public PhotoDtoOut toDto(PhotoModel photo) {
        UserDtoOut userDTO;

        try {
            userDTO = userService.toDto(photo.getUser());

            return new PhotoDtoOut(String.valueOf(photo.getId()), photo.getName(),
                    photo.getDescription(),
                    photo.getLikecount(),
                    photo.getViewcount(),
                    photo.getUploaddate(),
                    userDTO,
                    photo.getCategory().getName(),
                    photo.getPhotofile(), photo.getLicense());

        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new PhotoDtoOut();
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
                        photo.getPhotofile(), photo.getLicense());

                res.add(photoDTO);
            }
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }


    public List<PhotoDtoOutSmall> toDtoSmall(List<PhotoModel> entities) {
        List<PhotoDtoOutSmall> res = new ArrayList<>();
        PhotoDtoOutSmall photoDTO;

        try {
            if(entities != null)
                for (PhotoModel photo : entities) {

                    photoDTO = new PhotoDtoOutSmall(String.valueOf(photo.getId()),
                            photo.getUploaddate(),
                            photo.getPhotofile());

                    res.add(photoDTO);
                }
        } catch (Exception ex) {
            Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Transactional
    public PhotoModel updateViewCount(long photoId) {

        Optional<PhotoModel> photoOptional = photoRepository.findById(photoId);
        if (photoOptional.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (userService.findByEmail(auth.getName()) != photoOptional.get().getUser()) {
                photoOptional.get().updateViewCount();
                return photoRepository.save(photoOptional.get());
            }
            return photoOptional.get();
        }
        else throw new IllegalArgumentException("Фотография недоступна");
    }

    @Transactional
    public void likePhoto(Long photoId) {

        Optional<PhotoModel> photoOptional = photoRepository.findById(photoId);
        if (photoOptional.isPresent()) {
            PhotoModel photo = photoOptional.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel user = userService.findByEmail(auth.getName());

            LikeModel like = new LikeModel(photo, user);
                likeRepository.save(like);

            photo.getLikes().add(like);
            photo.updateLikeCount();
            photoRepository.save(photo);
        }

        else throw new IllegalArgumentException("Фотография недоступна");
    }

    @Transactional
    public void dislikePhoto(Long photoId) {

        Optional<PhotoModel> photoOptional = photoRepository.findById(photoId);
        if (photoOptional.isPresent()) {
            PhotoModel photo = photoOptional.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserModel user = userService.findByEmail(auth.getName());

            likeRepository.deleteByPhotoAndUser(photo, user);

            photo.getLikes().removeIf(l -> l.getUser() == user);

            photo.updateLikeCount();
            photoRepository.save(photo);
        }

        else throw new IllegalArgumentException("Фотография недоступна");
    }

}
