package com.course.PhotoSocial.service;

import com.course.PhotoSocial.model.CategoryModel;
import com.course.PhotoSocial.model.PhotoModel;
import com.course.PhotoSocial.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

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
        return null;
    }

    public List<PhotoModel> getNewPhotos() {
        //todo
        return null;
    }

    public List<PhotoModel> findByCategory(CategoryModel category) {
        return photoRepository.findByCategory(category);
    }
}
