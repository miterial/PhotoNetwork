package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.dto.ServiceDto;
import com.course.PhotoNetwork.model.dto.UserServicesDtoIn;
import com.course.PhotoNetwork.repository.BookingRepository;
import com.course.PhotoNetwork.repository.UserRepository;
import com.course.PhotoNetwork.model.types.ServiceEnum;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.ServiceDtoSmall;
import com.course.PhotoNetwork.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public List<ServiceDtoSmall> toDto(List<ServiceModel> all) {
        List<ServiceDtoSmall> res = new ArrayList<>();

        all.forEach(a ->{
            ServiceDtoSmall out = new ServiceDtoSmall(a.getName());
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

    public List<ServiceDtoSmall> getDefaultServices() {
        List<ServiceDtoSmall> res = new ArrayList<>();
        ServiceEnum[] defaults = ServiceEnum.values();

        for(ServiceEnum d : defaults) {
            res.add(new ServiceDtoSmall(d.getVal()));
        }
        return res;
    }

    public void changeServices(UserServicesDtoIn services) {
        Optional<UserModel> userOptional = userRepository.findById(services.getUserId());
        if(!userOptional.isPresent()) {
            throw new IllegalArgumentException("User is not found");
        }

        UserModel user = userOptional.get();
        List<ServiceModel> res = toEntity(services.getServices(),user);
        serviceRepository.saveAll(res);

        if(user.getServices() == null)
            user.setServices(new ArrayList<>());

        user.getServices().addAll(res);
        userRepository.save(user);
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

    public Optional<ServiceModel> findById(long serviceId) {
        return serviceRepository.findById(serviceId);
    }
}
