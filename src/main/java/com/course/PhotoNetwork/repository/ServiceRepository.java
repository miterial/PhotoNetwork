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
import java.util.Set;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, Long> {
    Optional<ServiceModel> findByName(String serviceName);

    void deleteByName(String serviceName);

    Set<ServiceModel> findByMaster(UserModel master);

    void deleteByMaster(UserModel user);

    ServiceModel findByMasterAndName(UserModel master, String name);

    List<ServiceModel> findByNameAndMasterIsNotNull(String serviceName);
}
