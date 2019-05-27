package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.exceptions.ServiceIsInUseException;
import com.course.PhotoNetwork.model.RoleModel;
import com.course.PhotoNetwork.model.dto.ServiceDto;
import com.course.PhotoNetwork.model.dto.UserServicesDto;
import com.course.PhotoNetwork.repository.BookingRepository;
import com.course.PhotoNetwork.repository.RoleRepository;
import com.course.PhotoNetwork.repository.UserRepository;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.ServiceDtoSmall;
import com.course.PhotoNetwork.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    public List<ServiceModel> findAll() {
        return serviceRepository.findAll();
    }

    public List<ServiceDtoSmall> toDtoSmall(List<ServiceModel> all) {
        List<ServiceDtoSmall> res = new ArrayList<>();

        all.forEach(a ->{
            ServiceDtoSmall out = new ServiceDtoSmall(a.getName());
            res.add(out);
        });

        return res;
    }

    public Set<ServiceDto> toDto(Set<ServiceModel> all) {
        Set<ServiceDto> res = new HashSet<>();

        all.forEach(a ->{
            ServiceDto out = new ServiceDto(a.getId(),a.getName(),a.getPrice());
            res.add(out);
        });

        return res;
    }

    public UserServicesDto toListDto(Set<ServiceModel> entities, Long userId) {
        return new UserServicesDto(toDto(entities),userId);
    }

    public ServiceDto toDto(ServiceModel entity) {
        return new ServiceDto(entity.getId(),entity.getName(),entity.getPrice());
    }

    public ServiceModel save(ServiceModel service) {
        return serviceRepository.save(service);
    }

    public Optional<ServiceModel> findByName(String serviceName) {
        return serviceRepository.findByName(serviceName);
    }

    /**
     * Delete service only if no master provides it
     * @param serviceName
     */
    public void deleteByName(String serviceName) {
        if(!serviceRepository.findByNameAndMaster(serviceName, null).isEmpty())
            throw new ServiceIsInUseException();

        serviceRepository.deleteByName(serviceName);
    }

    /**
     * Default service is a service where price == 0
     * @return list
     */
    public Set<ServiceDto> getDefaultServices() {
        Set<ServiceDto> res = new HashSet<>();
        Set<ServiceModel> defaults = serviceRepository.findByMaster(null);

        for(ServiceModel d : defaults) {
            res.add(new ServiceDto(d.getId(),d.getName(),d.getPrice()));
        }
        return res;
    }

    @Transactional
    public Set<ServiceModel> changeServices(UserServicesDto services) {
        Optional<UserModel> userOptional = userService.findById(services.getUserId());
        if(!userOptional.isPresent()) {
            throw new IllegalArgumentException("User is not found");
        }

        UserModel user = userOptional.get();
        Set<ServiceModel> res = toEntity(services.getServices(),user);

        serviceRepository.deleteByMaster(user);
        serviceRepository.saveAll(res);

        if(!userService.isMaster(user))
            user.getRoles().add(roleRepository.findByRolename("MASTER"));
        user.setServices(res);

        userService.save(user);

        return res;
    }

    public Optional<ServiceModel> findById(long serviceId) {
        return serviceRepository.findById(serviceId);
    }

    public void deleteById(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    public Set<ServiceModel> findByMaster(UserModel user) {
        return serviceRepository.findByMaster(user);
    }

    private Set<ServiceModel> toEntity(Set<ServiceDto> services, UserModel userModel) {
        Set<ServiceModel> res = new HashSet<>();

        services.forEach(a -> {
            ServiceModel service = new ServiceModel();
            service.setName(a.getName());
            service.setPrice(a.getPrice());
            service.setMaster(userModel);
            res.add(service);
        });

        return res;
    }

    public ServiceModel findByMasterAndName(String masterId, String serviceId) {
        ServiceModel service = serviceRepository.findById(Long.parseLong(serviceId)).get();
        UserModel master = userService.findById(Long.parseLong(masterId)).get();
        return serviceRepository.findByMasterAndName(master,service.getName());
    }
}
