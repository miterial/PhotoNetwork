package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.ServiceDto;
import com.course.PhotoNetwork.model.dto.ServiceDtoSmall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, Long> {
    Optional<ServiceModel> findByName(String serviceName);

    void deleteByName(String serviceName);

    List<ServiceModel> findByPrice(double price);

    List<ServiceModel> findByMaster(UserModel master);

    void deleteByMaster(UserModel user);

}
