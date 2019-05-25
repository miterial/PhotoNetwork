package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.dto.ServiceDto;
import com.course.PhotoNetwork.model.dto.UserServicesDtoIn;
import com.course.PhotoNetwork.repository.BookingRepository;
import com.course.PhotoNetwork.repository.UserRepository;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.ServiceDtoSmall;
import com.course.PhotoNetwork.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

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

    public Set<ServiceDto> toDto(List<ServiceModel> all) {
        Set<ServiceDto> res = new HashSet<>();

        all.forEach(a ->{
            ServiceDto out = new ServiceDto(a.getId(),a.getName(),a.getPrice());
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

    /**
     * Default service is a service where price == 0
     * @return list
     */
    public Set<ServiceDto> getDefaultServices() {
        Set<ServiceDto> res = new HashSet<>();
        List<ServiceModel> defaults = serviceRepository.findByPrice(0);

        for(ServiceModel d : defaults) {
            res.add(new ServiceDto(d.getId(),d.getName(),d.getPrice()));
        }
        return res;
    }

    @Transactional
    public void changeServices(UserServicesDtoIn services) {
        Optional<UserModel> userOptional = userRepository.findById(services.getUserId());
        if(!userOptional.isPresent()) {
            throw new IllegalArgumentException("User is not found");
        }

        UserModel user = userOptional.get();
        List<ServiceModel> res = toEntity(services.getServices(),user);

        serviceRepository.deleteByMaster(user);
        serviceRepository.saveAll(res);

        user.setServices(res);

        userRepository.save(user);
    }

    public Optional<ServiceModel> findById(long serviceId) {
        return serviceRepository.findById(serviceId);
    }

    public void deleteById(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    public List<ServiceModel> findByMaster(UserModel user) {
        return serviceRepository.findByMaster(user);
    }

    private List<ServiceModel> toEntity(List<ServiceDto> services, UserModel userModel) {
        List<ServiceModel> res = new ArrayList<>();

        services.forEach(a -> {
            ServiceModel service = new ServiceModel();
            service.setName(a.getName());
            service.setPrice(a.getPrice());
            service.setMaster(userModel);
            res.add(service);
        });

        return res;
    }

    public Set<ServiceDto> excludeDefaultExisting(List<ServiceDto> services) {

        return null;
    }
}
