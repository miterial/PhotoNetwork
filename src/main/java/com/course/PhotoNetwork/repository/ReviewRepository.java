package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {
}
