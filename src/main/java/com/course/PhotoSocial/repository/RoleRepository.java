package com.course.PhotoSocial.repository;

import com.course.PhotoSocial.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    RoleModel findByRolename(String roleName);
}
