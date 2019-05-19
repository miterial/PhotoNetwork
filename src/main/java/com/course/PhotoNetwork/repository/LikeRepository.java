package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.LikeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeModel, Long> {
    void deleteByPhotoAndUser(PhotoModel photo, UserModel user);

    LikeModel findByPhotoAndUser(PhotoModel photo, UserModel user);
}
