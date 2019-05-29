package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.PhotoModel;
import com.course.PhotoNetwork.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);

}
