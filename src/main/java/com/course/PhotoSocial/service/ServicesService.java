package com.course.PhotoSocial.service;

import com.course.PhotoSocial.model.ServiceEnum;
import com.course.PhotoSocial.model.ServiceModel;
import com.course.PhotoSocial.model.UserModel;
import com.course.PhotoSocial.model.dto.ServiceDtoIn;
import com.course.PhotoSocial.model.dto.ServiceDtoOut;
import com.course.PhotoSocial.model.dto.UserDtoOut;
import com.course.PhotoSocial.model.dto.UserServicesDtoIn;
import com.course.PhotoSocial.repository.ServiceRepository;
import com.course.PhotoSocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ServiceModel> findAll() {
        return serviceRepository.findAll();
    }

    public List<ServiceDtoOut> toDto(List<ServiceModel> all) {
        List<ServiceDtoOut> res = new ArrayList<>();

        all.forEach(a ->{
            ServiceDtoOut out = new ServiceDtoOut(a.getName());
            res.add(out);
        });

        return res;
    }

    public void save(ServiceModel service) {
        serviceRepository.save(service);
    }

    public Optional<ServiceModel> findByName(String serviceName) {
        return serviceRepository.findByName(serviceName);
    }

    public void deleteByName(String serviceName) {
        serviceRepository.deleteByName(serviceName);
    }

    public List<ServiceDtoOut> getDefaultServices() {
        List<ServiceDtoOut> res = new ArrayList<>();
        ServiceEnum[] defaults = ServiceEnum.values();

        for(ServiceEnum d : defaults) {
            res.add(new ServiceDtoOut(d.getVal()));
        }
        return res;
    }

    public void changeServices(UserServicesDtoIn services) {
        Optional<UserModel> user = userRepository.findById(services.getUserId());
        if(!user.isPresent()) {
            throw new IllegalArgumentException("User is not found");
        }

        List<ServiceModel> res = toEntity(services.getServices(),user.get());

        serviceRepository.saveAll(res);

    }

    private List<ServiceModel> toEntity(List<ServiceDtoIn> services, UserModel userModel) {
        List<ServiceModel> res = new ArrayList<>();

        services.forEach(a -> {
            ServiceModel service = new ServiceModel();
            service.setName(a.getName());
            service.setPrice(a.getPrice());
            service.setUser(userModel);
            res.add(service);
        });

        return res;
    }
}
