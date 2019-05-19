package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, Long> {
    Optional<ServiceModel> findByName(String serviceName);

    void deleteByName(String serviceName);
}
