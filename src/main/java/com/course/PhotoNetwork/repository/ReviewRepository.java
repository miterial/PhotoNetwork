package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.ReviewModel;
import com.course.PhotoNetwork.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {
    void deleteByAuthor(UserModel author);
}
